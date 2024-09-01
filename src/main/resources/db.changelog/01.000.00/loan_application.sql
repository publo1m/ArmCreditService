CREATE TABLE loan_application (
    loan_application_id uuid PRIMARY KEY,
    client_id uuid, FOREIGN KEY (client_id) REFERENCES client(client_id),
    is_approved_loan BOOLEAN NOT NULL,
    loan_amount DECIMAL(16,2) NOT NULL,
    loan_term_months SMALLINT NOT NULL
);