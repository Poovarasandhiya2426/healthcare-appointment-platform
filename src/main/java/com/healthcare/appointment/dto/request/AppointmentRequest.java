
package com.healthcare.appointment.dto.request;

import com.healthcare.appointment.enums.AppointmentType;
import com.healthcare.appointment.enums.ConsultationMode;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {

    // Patient ID validation
    @NotNull(message = "Patient ID is required")
    @Positive(message = "Patient ID must be greater than zero")
    private Long patientId;

    // Doctor ID validation
    @NotNull(message = "Doctor ID is required")
    @Positive(message = "Doctor ID must be greater than zero")
    private Long doctorId;

    // Hospital ID validation (optional)
    @Positive(message = "Hospital ID must be greater than zero")
    private Long hospitalId;

    // Appointment date validation
    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    // Appointment start time
    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    // Appointment end time
    @NotNull(message = "End time is required")
    private LocalTime endTime;

    // Appointment type
    @NotNull(message = "Appointment type is required")
    private AppointmentType appointmentType;

    // Consultation mode
    @NotNull(message = "Consultation mode is required")
    private ConsultationMode consultationMode;

    // Appointment reason
    @NotBlank(message = "Appointment reason is required")
    @Size(
            max = 1000,
            message = "Reason cannot exceed 1000 characters"
    )
    private String reason;
}
