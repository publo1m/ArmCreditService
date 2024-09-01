CREATE TABLE client (
    client_id uuid PRIMARY KEY ,
    family_name VARCHAR(255) NOT NULL ,
    name VARCHAR(255) NOT NULL ,
    patronymic VARCHAR(255) NOT NULL ,
    passport_details VARCHAR(10) NOT NULL ,
    marital_status VARCHAR(20) NOT NULL CHECK ( marital_status IN ('Single', 'Married', 'Divorced', 'Widowed')) ,
    registration VARCHAR(255) NOT NULL ,
    contact_number VARCHAR(11) NOT NULL ,
    employment_details VARCHAR(500) NOT NULL
);