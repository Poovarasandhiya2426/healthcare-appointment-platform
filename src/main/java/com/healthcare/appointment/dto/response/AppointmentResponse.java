
package com.healthcare.appointment.dto.response;

import com.healthcare.appointment.enums.AppointmentStatus;
import com.healthcare.appointment.enums.AppointmentType;
import com.healthcare.appointment.enums.ConsultationMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;

    private Long patientId;

    private Long doctorId;

    private Long hospitalId;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private AppointmentStatus status;

    private AppointmentType appointmentType;

    private ConsultationMode consultationMode;

    private String reason;

    private String doctorNotes;

    private String cancellationReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
