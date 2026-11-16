
package com.healthcare.appointment.dto.request;

import com.healthcare.appointment.enums.AppointmentStatus;
import com.healthcare.appointment.enums.AppointmentType;

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

    private Long patientId;

    private Long doctorId;

    private Long hospitalId;

    private LocalDate fromDate;

    private LocalDate toDate;

    private AppointmentStatus status;

    private AppointmentType appointmentType;
}
