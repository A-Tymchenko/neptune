CREATE TABLE IF NOT EXISTS flight (
  id INT NOT NULL AUTO_INCREMENT UNIQUE,
  name VARCHAR(255) NOT NULL,
  carrier VARCHAR(255),
  duration TIME, 
  meal BOOLEAN,
  fare DECIMAL,
  depaparture_date DATETIME,
  arrival_date DATETIME,
);
