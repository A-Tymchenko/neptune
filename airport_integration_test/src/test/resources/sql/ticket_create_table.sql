CREATE TABLE IF NOT EXISTS TICKET(
    ID_TICKET       INT AUTO_INCREMENT PRIMARY KEY,
    TICKET_NUMBER   VARCHAR(24),
    PASSENGER_NAME  VARCHAR(64),
    DOCUMENT        VARCHAR(24),
    SELLING_DATE    TIMESTAMP,
    );


