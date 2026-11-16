
CREATE INDEX idx_appointment_patient
    ON appointments(patient_id);

CREATE INDEX idx_appointment_doctor
    ON appointments(doctor_id);

CREATE INDEX idx_appointment_date
    ON appointments(appointment_date);

CREATE INDEX idx_appointment_status
    ON appointments(status);
