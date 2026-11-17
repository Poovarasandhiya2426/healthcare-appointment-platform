
package com.healthcare.appointment.client;

import com.healthcare.appointment.config.OpenFeignConfig;
import com.healthcare.appointment.dto.response.PatientServiceResponse;
import com.healthcare.appointment.dto.response.ServiceApiResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "patient-service",
        url = "${services.patient.url}",
        configuration = OpenFeignConfig.class
)
public interface PatientServiceClient {

    // Get patient details by patient ID
    @GetMapping("/api/patients/{patientId}")
    ServiceApiResponse<PatientServiceResponse> getPatientById(
            @PathVariable("patientId") Long patientId
    );
}
