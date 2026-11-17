
package com.healthcare.appointment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;
}
