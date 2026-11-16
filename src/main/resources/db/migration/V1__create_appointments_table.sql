
CREATE TABLE appointments (

                              id BIGINT NOT NULL AUTO_INCREMENT,

                              patient_id BIGINT NOT NULL,

                              doctor_id BIGINT NOT NULL,

                              hospital_id BIGINT,

                              appointment_date DATE NOT NULL,

                              start_time TIME NOT NULL,

                              end_time TIME NOT NULL,

                              status VARCHAR(30) NOT NULL,

                              appointment_type VARCHAR(50) NOT NULL,

                              consultation_mode VARCHAR(30) NOT NULL,

                              reason VARCHAR(1000),

                              doctor_notes VARCHAR(2000),

                              cancellation_reason VARCHAR(1000),

                              created_at DATETIME NOT NULL,

                              updated_at DATETIME,

                              PRIMARY KEY (id)

);
