DROP TABLE IF EXISTS AIRPORT;

CREATE TABLE IF NOT EXISTS AIRPORT (
    APID INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
    APNAME VARCHAR(255) NOT NULL,
    APNUM  VARCHAR(255) NOT NULL,
    APTYPE VARCHAR(255) NOT NULL,
    ADDRESS VARCHAR(255) NOT NULL,
    TERMINALCOUNT INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS flight (
  flId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  carrier VARCHAR(255),
  duration TIME, 
  meal_on BOOLEAN DEFAULT FALSE,
  fare DECIMAL,
  departure_date DATETIME,
  arrival_date DATETIME,
);

CREATE TABLE IF NOT EXISTS plane (
  planeId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  owner VARCHAR(255),
  model VARCHAR(255),
  type VARCHAR(255),
  platenumber INTEGER,
);


CREATE TABLE IF NOT EXISTS TICKET(
    TICKET_ID       INT AUTO_INCREMENT PRIMARY KEY,
    TICKET_NUMBER   VARCHAR(24),
    PASSENGER_NAME  VARCHAR(64),
    DOCUMENT        VARCHAR(24),
    SELLING_DATE    TIMESTAMP,
    );
