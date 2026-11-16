
package com.healthcare.appointment.service;

import com.healthcare.appointment.dto.request.AppointmentRequest;
import com.healthcare.appointment.dto.request.AppointmentUpdateRequest;
import com.healthcare.appointment.dto.request.AppointmentRescheduleRequest;
import com.healthcare.appointment.dto.request.AppointmentSearchRequest;

import com.healthcare.appointment.dto.response.AppointmentResponse;
import com.healthcare.appointment.dto.response.AppointmentSummaryResponse;

import com.healthcare.appointment.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {

    // Book a new appointment
    AppointmentResponse bookAppointment(AppointmentRequest request);

    // Update existing appointment
    AppointmentResponse updateAppointment(
            Long appointmentId,
            AppointmentUpdateRequest request
    );

    // Reschedule appointment
    AppointmentResponse rescheduleAppointment(
            Long appointmentId,
            AppointmentRescheduleRequest request
    );

    // Cancel appointment
    AppointmentResponse cancelAppointment(
            Long appointmentId,
            String cancellationReason
    );

    // Get appointment by ID
    AppointmentResponse getAppointmentById(Long appointmentId);

    // Get all appointments
    List<AppointmentSummaryResponse> getAllAppointments();

    // Get appointments by patient ID
    List<AppointmentSummaryResponse> getAppointmentsByPatientId(
            Long patientId
    );

    // Get appointments by doctor ID
    List<AppointmentSummaryResponse> getAppointmentsByDoctorId(
            Long doctorId
    );

    // Get appointments by hospital ID
    List<AppointmentSummaryResponse> getAppointmentsByHospitalId(
            Long hospitalId
    );

    // Get appointments by status
    List<AppointmentSummaryResponse> getAppointmentsByStatus(
            AppointmentStatus status
    );

    // Search appointments using dynamic filters
    List<AppointmentSummaryResponse> searchAppointments(
            AppointmentSearchRequest request
    );

    // Confirm appointment
    AppointmentResponse confirmAppointment(Long appointmentId);

    // Complete appointment
    AppointmentResponse completeAppointment(Long appointmentId);
}
