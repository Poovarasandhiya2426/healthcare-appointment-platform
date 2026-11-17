
package com.healthcare.appointment.mapper;

import com.healthcare.appointment.dto.request.AppointmentRequest;
import com.healthcare.appointment.dto.request.AppointmentUpdateRequest;

import com.healthcare.appointment.dto.response.AppointmentResponse;
import com.healthcare.appointment.dto.response.AppointmentSummaryResponse;

import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.enums.AppointmentStatus;

import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    // 1. Convert AppointmentRequest to Appointment Entity
    public Appointment toEntity(AppointmentRequest request) {

        return Appointment.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .hospitalId(request.getHospitalId())
                .appointmentDate(request.getAppointmentDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(AppointmentStatus.PENDING)
                .appointmentType(request.getAppointmentType())
                .consultationMode(request.getConsultationMode())
                .reason(request.getReason())
                .build();
    }

    // 2. Convert Appointment Entity to AppointmentResponse
    public AppointmentResponse toResponse(Appointment appointment) {

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .hospitalId(appointment.getHospitalId())
                .appointmentDate(appointment.getAppointmentDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .appointmentType(appointment.getAppointmentType())
                .consultationMode(appointment.getConsultationMode())
                .reason(appointment.getReason())
                .doctorNotes(appointment.getDoctorNotes())
                .cancellationReason(appointment.getCancellationReason())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    // 3. Convert Appointment Entity to Summary Response
    public AppointmentSummaryResponse toSummaryResponse(
            Appointment appointment) {

        return AppointmentSummaryResponse.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .hospitalId(appointment.getHospitalId())
                .appointmentDate(appointment.getAppointmentDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .appointmentType(appointment.getAppointmentType())
                .consultationMode(appointment.getConsultationMode())
                .build();
    }

    // 4. Update existing Appointment Entity
    public void updateEntity(
            Appointment appointment,
            AppointmentUpdateRequest request) {

        appointment.setAppointmentDate(
                request.getAppointmentDate()
        );

        appointment.setStartTime(
                request.getStartTime()
        );

        appointment.setEndTime(
                request.getEndTime()
        );

        appointment.setAppointmentType(
                request.getAppointmentType()
        );

        appointment.setConsultationMode(
                request.getConsultationMode()
        );

        appointment.setReason(
                request.getReason()
        );
    }
}
