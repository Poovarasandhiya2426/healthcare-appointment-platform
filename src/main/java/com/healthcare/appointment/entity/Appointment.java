
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

                @Index(
                        name = "idx_appointment_patient",
                        columnList = "patient_id"
                ),

                @Index(
                        name = "idx_appointment_doctor",
                        columnList = "doctor_id"
                ),

                @Index(
                        name = "idx_appointment_hospital",
                        columnList = "hospital_id"
                ),

                @Index(
                        name = "idx_appointment_date",
                        columnList = "appointment_date"
                ),

                @Index(
                        name = "idx_appointment_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_appointment_doctor_date_status",
                        columnList = "doctor_id, appointment_date, status"
                )
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

    // Patient reference
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    // Doctor reference
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    // Hospital reference
    @Column(name = "hospital_id")
    private Long hospitalId;

    // Appointment date
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    // Appointment start time
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    // Appointment end time
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // Appointment status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status;

    // Appointment type
    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false, length = 50)
    private AppointmentType appointmentType;

    // Consultation mode
    @Enumerated(EnumType.STRING)
    @Column(name = "consultation_mode", nullable = false, length = 30)
    private ConsultationMode consultationMode;

    // Appointment reason
    @Column(length = 1000)
    private String reason;

    // Doctor notes
    @Column(name = "doctor_notes", length = 2000)
    private String doctorNotes;

    // Cancellation reason
    @Column(name = "cancellation_reason", length = 1000)
    private String cancellationReason;

    // Created timestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    // Updated timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Automatically execute before inserting a record
    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = AppointmentStatus.PENDING;
        }
    }

    // Automatically execute before updating a record
    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}
