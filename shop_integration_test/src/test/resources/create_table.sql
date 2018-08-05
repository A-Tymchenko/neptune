CREATE TABLE IF NOT EXISTS warehouse (
  id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
  name VARCHAR(255),
  price DOUBLE,
  amount INT);

CREATE TABLE IF NOT EXISTS GOODS (
 ID BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
 NAME VARCHAR(255),
 BARCODE BIGINT,
 PRICE FLOAT);

CREATE TABLE IF NOT EXISTS ORDERS(
    ORDER_ID IDENTITY AUTO_INCREMENT PRIMARY KEY,
    NUMBER INT,
    PRICE DOUBLE,
    DELIVERY_INCLUDED BOOLEAN,
    DELIVERY_COST INT,
    EXECUTED BOOLEAN);

CREATE TABLE IF NOT EXISTS USERS(
  USER_ID INT AUTO_INCREMENT PRIMARY KEY,
  PHONE_NUMBER VARCHAR(35),
  NAME VARCHAR(250),
  SECOND_NAME VARCHAR(250),
  COUNTRY VARCHAR(250),
  EMAIL_ADDRESS VARCHAR(250));