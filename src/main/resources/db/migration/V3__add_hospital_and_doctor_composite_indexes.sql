
-- Add index for hospital-based appointment searches

CREATE INDEX idx_appointment_hospital
    ON appointments(hospital_id);


-- Add composite index for doctor availability checks

CREATE INDEX idx_appointment_doctor_date_status
    ON appointments(doctor_id, appointment_date, status);
