
package com.healthcare.appointment.exception;

import lombok.Getter;

@Getter
public class ExternalServiceException extends RuntimeException {

    private final String errorCode;

    public ExternalServiceException(String message) {
        super(message);
        this.errorCode = "EXTERNAL_SERVICE_ERROR";
    }

    public ExternalServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "EXTERNAL_SERVICE_ERROR";
    }
}
