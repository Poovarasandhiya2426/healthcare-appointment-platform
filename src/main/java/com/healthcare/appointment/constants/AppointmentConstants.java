
package com.healthcare.appointment.constants;

public final class AppointmentConstants {

    private AppointmentConstants() {
        // Prevent object creation
    }

    public static final String APPOINTMENT_NOT_FOUND =
            "Appointment not found with ID: ";

    public static final String APPOINTMENT_ALREADY_CANCELLED =
            "Appointment is already cancelled";

    public static final String COMPLETED_APPOINTMENT_CANNOT_CANCEL =
            "Completed appointment cannot be cancelled";

    public static final String NO_SHOW_APPOINTMENT_CANNOT_CANCEL =
            "No-show appointment cannot be cancelled";

    public static final String CANCELLATION_REASON_REQUIRED =
            "Cancellation reason is required";

    public static final String APPOINTMENT_TIME_REQUIRED =
            "Appointment date and time are required";

    public static final String INVALID_APPOINTMENT_TIME =
            "End time must be after start time";

    public static final String APPOINTMENT_DATE_TIME_IN_PAST =
            "Appointment date and time cannot be in the past";

    public static final String DOCTOR_SLOT_CONFLICT =
            "Doctor already has an appointment during this time slot";

    public static final String APPOINTMENT_CANNOT_BE_MODIFIED =
            "This appointment cannot be modified";

    public static final String ONLY_PENDING_CAN_CONFIRM =
            "Only pending appointments can be confirmed";

    public static final String ONLY_CONFIRMED_CAN_COMPLETE =
            "Only confirmed appointments can be completed";

    public static final String INVALID_DATE_RANGE =
            "From date cannot be after to date";
}
