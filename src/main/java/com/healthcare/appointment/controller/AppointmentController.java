
package com.healthcare.appointment.controller;

import com.healthcare.appointment.dto.request.AppointmentRequest;
import com.healthcare.appointment.dto.request.AppointmentUpdateRequest;
import com.healthcare.appointment.dto.request.AppointmentRescheduleRequest;
import com.healthcare.appointment.dto.request.AppointmentSearchRequest;

import com.healthcare.appointment.dto.response.ApiResponse;
import com.healthcare.appointment.dto.response.AppointmentResponse;
import com.healthcare.appointment.dto.response.AppointmentSummaryResponse;

import com.healthcare.appointment.enums.AppointmentStatus;

import com.healthcare.appointment.service.AppointmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    // 1. Book a new appointment
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        AppointmentResponse response =
                appointmentService.bookAppointment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Appointment booked successfully",
                        response
                ));
    }

    // 2. Update existing appointment
    @PutMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentUpdateRequest request) {

        AppointmentResponse response =
                appointmentService.updateAppointment(
                        appointmentId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment updated successfully",
                        response
                )
        );
    }

    // 3. Reschedule appointment
    @PutMapping("/{appointmentId}/reschedule")
    public ResponseEntity<ApiResponse<AppointmentResponse>> rescheduleAppointment(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentRescheduleRequest request) {

        AppointmentResponse response =
                appointmentService.rescheduleAppointment(
                        appointmentId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment rescheduled successfully",
                        response
                )
        );
    }

    // 4. Cancel appointment
    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancelAppointment(
            @PathVariable Long appointmentId,
            @RequestParam
            @NotBlank(message = "Cancellation reason is required")
            @Size(max = 1000, message = "Cancellation reason cannot exceed 1000 characters")
            String cancellationReason) {

        AppointmentResponse response =
                appointmentService.cancelAppointment(
                        appointmentId,
                        cancellationReason
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment cancelled successfully",
                        response
                )
        );
    }

    // 5. Get appointment by ID
    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(
            @PathVariable Long appointmentId) {

        AppointmentResponse response =
                appointmentService.getAppointmentById(appointmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment retrieved successfully",
                        response
                )
        );
    }

    // 6. Get all appointments
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentSummaryResponse>>> getAllAppointments() {

        List<AppointmentSummaryResponse> response =
                appointmentService.getAllAppointments();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments retrieved successfully",
                        response
                )
        );
    }

    // 7. Get appointments by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<AppointmentSummaryResponse>>> getAppointmentsByPatientId(
            @PathVariable Long patientId) {

        List<AppointmentSummaryResponse> response =
                appointmentService.getAppointmentsByPatientId(patientId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient appointments retrieved successfully",
                        response
                )
        );
    }

    // 8. Get appointments by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentSummaryResponse>>> getAppointmentsByDoctorId(
            @PathVariable Long doctorId) {

        List<AppointmentSummaryResponse> response =
                appointmentService.getAppointmentsByDoctorId(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor appointments retrieved successfully",
                        response
                )
        );
    }

    // 9. Get appointments by hospital ID
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<ApiResponse<List<AppointmentSummaryResponse>>> getAppointmentsByHospitalId(
            @PathVariable Long hospitalId) {

        List<AppointmentSummaryResponse> response =
                appointmentService.getAppointmentsByHospitalId(hospitalId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Hospital appointments retrieved successfully",
                        response
                )
        );
    }

    // 10. Get appointments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AppointmentSummaryResponse>>> getAppointmentsByStatus(
            @PathVariable AppointmentStatus status) {

        List<AppointmentSummaryResponse> response =
                appointmentService.getAppointmentsByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments filtered by status successfully",
                        response
                )
        );
    }

    // 11. Search appointments using filters
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<AppointmentSummaryResponse>>> searchAppointments(
            @RequestBody AppointmentSearchRequest request) {

        List<AppointmentSummaryResponse> response =
                appointmentService.searchAppointments(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment search completed successfully",
                        response
                )
        );
    }

    // 12. Confirm appointment
    @PatchMapping("/{appointmentId}/confirm")
    public ResponseEntity<ApiResponse<AppointmentResponse>> confirmAppointment(
            @PathVariable Long appointmentId) {

        AppointmentResponse response =
                appointmentService.confirmAppointment(appointmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment confirmed successfully",
                        response
                )
        );
    }

    // 13. Complete appointment
    @PatchMapping("/{appointmentId}/complete")
    public ResponseEntity<ApiResponse<AppointmentResponse>> completeAppointment(
            @PathVariable Long appointmentId) {

        AppointmentResponse response =
                appointmentService.completeAppointment(appointmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment completed successfully",
                        response
                )
        );
    }
}
