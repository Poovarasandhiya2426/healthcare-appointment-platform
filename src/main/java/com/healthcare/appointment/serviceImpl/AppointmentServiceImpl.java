
package com.healthcare.appointment.serviceImpl;

import com.healthcare.appointment.constants.AppointmentConstants;

import com.healthcare.appointment.dto.request.AppointmentRequest;
import com.healthcare.appointment.dto.request.AppointmentUpdateRequest;
import com.healthcare.appointment.dto.request.AppointmentRescheduleRequest;
import com.healthcare.appointment.dto.request.AppointmentSearchRequest;

import com.healthcare.appointment.dto.response.AppointmentResponse;
import com.healthcare.appointment.dto.response.AppointmentSummaryResponse;

import com.healthcare.appointment.entity.Appointment;

import com.healthcare.appointment.enums.AppointmentStatus;

import com.healthcare.appointment.exception.BadRequestException;
import com.healthcare.appointment.exception.ResourceNotFoundException;

import com.healthcare.appointment.mapper.AppointmentMapper;
import com.healthcare.appointment.repository.AppointmentRepository;
import com.healthcare.appointment.service.AppointmentService;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;

    /*
     * Active statuses are considered when checking
     * doctor appointment slot availability.
     *
     * RESCHEDULED is also included to prevent
     * another appointment from using the same slot.
     */
    private static final List<AppointmentStatus> ACTIVE_STATUSES =
            List.of(
                    AppointmentStatus.PENDING,
                    AppointmentStatus.CONFIRMED,
                    AppointmentStatus.RESCHEDULED
            );

    // 1. Book a new appointment
    @Override
    public AppointmentResponse bookAppointment(
            AppointmentRequest request) {

        validateAppointmentTime(
                request.getAppointmentDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        checkDoctorAvailability(
                request.getDoctorId(),
                request.getAppointmentDate(),
                request.getStartTime(),
                request.getEndTime(),
                null
        );

        Appointment appointment =
                appointmentMapper.toEntity(request);

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(savedAppointment);
    }

    // 2. Update existing appointment
    @Override
    public AppointmentResponse updateAppointment(
            Long appointmentId,
            AppointmentUpdateRequest request) {

        Appointment appointment =
                getAppointmentEntity(appointmentId);

        validateEditableAppointment(appointment);

        validateAppointmentTime(
                request.getAppointmentDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        checkDoctorAvailability(
                appointment.getDoctorId(),
                request.getAppointmentDate(),
                request.getStartTime(),
                request.getEndTime(),
                appointmentId
        );

        appointmentMapper.updateEntity(
                appointment,
                request
        );

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(updatedAppointment);
    }

    // 3. Reschedule appointment
    @Override
    public AppointmentResponse rescheduleAppointment(
            Long appointmentId,
            AppointmentRescheduleRequest request) {

        Appointment appointment =
                getAppointmentEntity(appointmentId);

        validateEditableAppointment(appointment);

        validateAppointmentTime(
                request.getNewAppointmentDate(),
                request.getNewStartTime(),
                request.getNewEndTime()
        );

        checkDoctorAvailability(
                appointment.getDoctorId(),
                request.getNewAppointmentDate(),
                request.getNewStartTime(),
                request.getNewEndTime(),
                appointmentId
        );

        appointment.setAppointmentDate(
                request.getNewAppointmentDate()
        );

        appointment.setStartTime(
                request.getNewStartTime()
        );

        appointment.setEndTime(
                request.getNewEndTime()
        );

        appointment.setStatus(
                AppointmentStatus.RESCHEDULED
        );

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(updatedAppointment);
    }

    // 4. Cancel appointment
    @Override
    public AppointmentResponse cancelAppointment(
            Long appointmentId,
            String cancellationReason) {

        Appointment appointment =
                getAppointmentEntity(appointmentId);

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BadRequestException(
                    AppointmentConstants.APPOINTMENT_ALREADY_CANCELLED
            );
        }

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BadRequestException(
                    AppointmentConstants.COMPLETED_APPOINTMENT_CANNOT_CANCEL
            );
        }

        if (appointment.getStatus() == AppointmentStatus.NO_SHOW) {
            throw new BadRequestException(
                    AppointmentConstants.NO_SHOW_APPOINTMENT_CANNOT_CANCEL
            );
        }

        if (cancellationReason == null ||
                cancellationReason.isBlank()) {

            throw new BadRequestException(
                    AppointmentConstants.CANCELLATION_REASON_REQUIRED
            );
        }

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        appointment.setCancellationReason(
                cancellationReason.trim()
        );

        Appointment cancelledAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(cancelledAppointment);
    }

    // 5. Get appointment by ID
    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(
            Long appointmentId) {

        Appointment appointment =
                getAppointmentEntity(appointmentId);

        return appointmentMapper.toResponse(appointment);
    }

    // 6. Get all appointments
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponse> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toSummaryResponse)
                .toList();
    }

    // 7. Get appointments by patient ID
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponse> getAppointmentsByPatientId(
            Long patientId) {

        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toSummaryResponse)
                .toList();
    }

    // 8. Get appointments by doctor ID
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponse> getAppointmentsByDoctorId(
            Long doctorId) {

        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toSummaryResponse)
                .toList();
    }

    // 9. Get appointments by hospital ID
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponse> getAppointmentsByHospitalId(
            Long hospitalId) {

        return appointmentRepository.findByHospitalId(hospitalId)
                .stream()
                .map(appointmentMapper::toSummaryResponse)
                .toList();
    }

    // 10. Get appointments by status
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponse> getAppointmentsByStatus(
            AppointmentStatus status) {

        return appointmentRepository.findByStatus(status)
                .stream()
                .map(appointmentMapper::toSummaryResponse)
                .toList();
    }

    // 11. Search appointments using dynamic filters
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponse> searchAppointments(
            AppointmentSearchRequest request) {

        if (request.getFromDate() != null &&
                request.getToDate() != null &&
                request.getFromDate().isAfter(request.getToDate())) {

            throw new BadRequestException(
                    AppointmentConstants.INVALID_DATE_RANGE
            );
        }

        Specification<Appointment> specification =
                (root, query, criteriaBuilder) -> {

                    Predicate predicate =
                            criteriaBuilder.conjunction();

                    if (request.getPatientId() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(
                                        root.get("patientId"),
                                        request.getPatientId()
                                )
                        );
                    }

                    if (request.getDoctorId() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(
                                        root.get("doctorId"),
                                        request.getDoctorId()
                                )
                        );
                    }

                    if (request.getHospitalId() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(
                                        root.get("hospitalId"),
                                        request.getHospitalId()
                                )
                        );
                    }

                    if (request.getFromDate() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.greaterThanOrEqualTo(
                                        root.get("appointmentDate"),
                                        request.getFromDate()
                                )
                        );
                    }

                    if (request.getToDate() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.lessThanOrEqualTo(
                                        root.get("appointmentDate"),
                                        request.getToDate()
                                )
                        );
                    }

                    if (request.getStatus() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(
                                        root.get("status"),
                                        request.getStatus()
                                )
                        );
                    }

                    if (request.getAppointmentType() != null) {
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(
                                        root.get("appointmentType"),
                                        request.getAppointmentType()
                                )
                        );
                    }

                    return predicate;
                };

        return appointmentRepository.findAll(specification)
                .stream()
                .map(appointmentMapper::toSummaryResponse)
                .toList();
    }

    // 12. Confirm appointment
    @Override
    public AppointmentResponse confirmAppointment(
            Long appointmentId) {

        Appointment appointment =
                getAppointmentEntity(appointmentId);

        /*
         * Both PENDING and RESCHEDULED appointments
         * can be confirmed.
         */
        if (appointment.getStatus() != AppointmentStatus.PENDING
                && appointment.getStatus() != AppointmentStatus.RESCHEDULED) {

            throw new BadRequestException(
                    AppointmentConstants.ONLY_PENDING_CAN_CONFIRM
            );
        }

        appointment.setStatus(
                AppointmentStatus.CONFIRMED
        );

        Appointment confirmedAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(confirmedAppointment);
    }

    // 13. Complete appointment
    @Override
    public AppointmentResponse completeAppointment(
            Long appointmentId) {

        Appointment appointment =
                getAppointmentEntity(appointmentId);

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new BadRequestException(
                    AppointmentConstants.ONLY_CONFIRMED_CAN_COMPLETE
            );
        }

        appointment.setStatus(
                AppointmentStatus.COMPLETED
        );

        Appointment completedAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(completedAppointment);
    }

    // 14. Find appointment or throw exception
    private Appointment getAppointmentEntity(
            Long appointmentId) {

        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppointmentConstants.APPOINTMENT_NOT_FOUND
                                        + appointmentId
                        )
                );
    }

    // 15. Validate appointment date and time
    private void validateAppointmentTime(
            LocalDate appointmentDate,
            LocalTime startTime,
            LocalTime endTime) {

        if (appointmentDate == null ||
                startTime == null ||
                endTime == null) {

            throw new BadRequestException(
                    AppointmentConstants.APPOINTMENT_TIME_REQUIRED
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException(
                    AppointmentConstants.INVALID_APPOINTMENT_TIME
            );
        }

        LocalDateTime appointmentStart =
                LocalDateTime.of(appointmentDate, startTime);

        if (appointmentStart.isBefore(LocalDateTime.now())) {
            throw new BadRequestException(
                    AppointmentConstants.APPOINTMENT_DATE_TIME_IN_PAST
            );
        }
    }

    // 16. Check doctor time-slot availability
    private void checkDoctorAvailability(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime startTime,
            LocalTime endTime,
            Long excludedAppointmentId) {

        List<Appointment> conflictingAppointments =
                appointmentRepository
                        .findByDoctorIdAndAppointmentDateAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
                                doctorId,
                                appointmentDate,
                                ACTIVE_STATUSES,
                                endTime,
                                startTime
                        );

        boolean hasConflict = conflictingAppointments.stream()
                .anyMatch(appointment ->
                        excludedAppointmentId == null ||
                                !appointment.getId().equals(excludedAppointmentId)
                );

        if (hasConflict) {
            throw new BadRequestException(
                    AppointmentConstants.DOCTOR_SLOT_CONFLICT
            );
        }
    }

    // 17. Validate whether appointment can be edited
    private void validateEditableAppointment(
            Appointment appointment) {

        if (appointment.getStatus() == AppointmentStatus.CANCELLED ||
                appointment.getStatus() == AppointmentStatus.COMPLETED ||
                appointment.getStatus() == AppointmentStatus.NO_SHOW) {

            throw new BadRequestException(
                    AppointmentConstants.APPOINTMENT_CANNOT_BE_MODIFIED
            );
        }
    }
}
