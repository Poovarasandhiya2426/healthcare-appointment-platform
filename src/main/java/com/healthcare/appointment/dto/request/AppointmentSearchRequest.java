
package com.healthcare.appointment.dto.request;

import com.healthcare.appointment.enums.AppointmentStatus;
import com.healthcare.appointment.enums.AppointmentType;

import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentSearchRequest {

    // Optional patient ID filter
    @Positive(message = "Patient ID must be greater than zero")
    private Long patientId;

    // Optional doctor ID filter
    @Positive(message = "Doctor ID must be greater than zero")
    private Long doctorId;

    // Optional hospital ID filter
    @Positive(message = "Hospital ID must be greater than zero")
    private Long hospitalId;

    // Search start date
    private LocalDate fromDate;

    // Search end date
    private LocalDate toDate;

    // Optional appointment status
    private AppointmentStatus status;

    // Optional appointment type
    private AppointmentType appointmentType;
}
