
package com.healthcare.appointment.dto.request;

import com.healthcare.appointment.enums.AppointmentType;
import com.healthcare.appointment.enums.ConsultationMode;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
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

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    private Long hospitalId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Appointment type is required")
    private AppointmentType appointmentType;

    @NotNull(message = "Consultation mode is required")
    private ConsultationMode consultationMode;

    @NotBlank(message = "Appointment reason is required")
    @Size(max = 1000, message = "Reason cannot exceed 1000 characters")
    private String reason;
}
