
package com.healthcare.appointment.repository;

import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.enums.AppointmentStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends
        JpaRepository<Appointment, Long>,
        JpaSpecificationExecutor<Appointment> {

    // Find appointments by patient ID
    List<Appointment> findByPatientId(Long patientId);

    // Find appointments by doctor ID
    List<Appointment> findByDoctorId(Long doctorId);

    // Find appointments by hospital ID
    List<Appointment> findByHospitalId(Long hospitalId);

    // Find appointments by status
    List<Appointment> findByStatus(AppointmentStatus status);

    // Find appointments by patient and status
    List<Appointment> findByPatientIdAndStatus(
            Long patientId,
            AppointmentStatus status
    );

    // Find appointments by doctor and date
    List<Appointment> findByDoctorIdAndAppointmentDate(
            Long doctorId,
            LocalDate appointmentDate
    );

    // Find appointments within a date range
    List<Appointment> findByAppointmentDateBetween(
            LocalDate fromDate,
            LocalDate toDate
    );

    // Find appointments by patient and date range
    List<Appointment> findByPatientIdAndAppointmentDateBetween(
            Long patientId,
            LocalDate fromDate,
            LocalDate toDate
    );

    // Find appointments by doctor and date range
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(
            Long doctorId,
            LocalDate fromDate,
            LocalDate toDate
    );

    // Find appointment by ID and patient ID
    Optional<Appointment> findByIdAndPatientId(
            Long id,
            Long patientId
    );

    // Check overlapping appointments for a doctor
    List<Appointment> findByDoctorIdAndAppointmentDateAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
            Long doctorId,
            LocalDate appointmentDate,
            List<AppointmentStatus> statuses,
            LocalTime endTime,
            LocalTime startTime
    );
}
