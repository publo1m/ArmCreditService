CREATE TABLE loan_agreement (
    loan_agreement_id uuid PRIMARY KEY,
    is_signed BOOLEAN NOT NULL,
    client_id uuid, FOREIGN KEY (client_id) REFERENCES client(client_id) ,
    loan_application_id uuid, FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id) ,
    date_of_signing TIMESTAMP
);