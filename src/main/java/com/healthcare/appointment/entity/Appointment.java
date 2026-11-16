
package com.healthcare.appointment.entity;

import com.healthcare.appointment.enums.AppointmentStatus;
import com.healthcare.appointment.enums.AppointmentType;
import com.healthcare.appointment.enums.ConsultationMode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "appointments",
        indexes = {
                @Index(name = "idx_appointment_patient", columnList = "patient_id"),
                @Index(name = "idx_appointment_doctor", columnList = "doctor_id"),
                @Index(name = "idx_appointment_date", columnList = "appointment_date"),
                @Index(name = "idx_appointment_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false, length = 50)
    private AppointmentType appointmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "consultation_mode", nullable = false, length = 30)
    private ConsultationMode consultationMode;

    @Column(length = 1000)
    private String reason;

    @Column(name = "doctor_notes", length = 2000)
    private String doctorNotes;

    @Column(name = "cancellation_reason", length = 1000)
    private String cancellationReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = AppointmentStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
