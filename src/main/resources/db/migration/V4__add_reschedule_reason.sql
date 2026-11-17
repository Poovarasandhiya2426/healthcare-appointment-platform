
-- Add reschedule reason column to appointments table

ALTER TABLE appointments
    ADD COLUMN reschedule_reason VARCHAR(1000) NULL;
