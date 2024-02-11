CREATE TABLE addresses (
    id integer PRIMARY KEY,
    zip_code varchar(7),
    prefecture varchar(20),
    city varchar(20),
    street_address varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS ADDRESS_ID_SEQ
    INCREMENT BY 1
    MAXVALUE 9999999999
    MINVALUE 1
    START WITH 1
;