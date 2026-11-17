
package com.healthcare.appointment.client;

import com.healthcare.appointment.config.OpenFeignConfig;
import com.healthcare.appointment.dto.response.DoctorServiceResponse;
import com.healthcare.appointment.dto.response.ServiceApiResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "doctor-service",
        url = "${services.doctor.url}",
        configuration = OpenFeignConfig.class
)
public interface DoctorServiceClient {

    // Get doctor details by doctor ID
    @GetMapping("/api/doctors/{doctorId}")
    ServiceApiResponse<DoctorServiceResponse> getDoctorById(
            @PathVariable("doctorId") Long doctorId
    );
}
