
package com.healthcare.appointment.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AppointmentRescheduleRequest {

    // New appointment date
    @NotNull(message = "New appointment date is required")
    @FutureOrPresent(
            message = "New appointment date cannot be in the past"
    )
    private LocalDate newAppointmentDate;

    // New appointment start time
    @NotNull(message = "New start time is required")
    private LocalTime newStartTime;

    // New appointment end time
    @NotNull(message = "New end time is required")
    private LocalTime newEndTime;

    // Reschedule reason
    @NotBlank(message = "Reschedule reason is required")
    @Size(
            max = 1000,
            message = "Reschedule reason cannot exceed 1000 characters"
    )
    private String rescheduleReason;
}
