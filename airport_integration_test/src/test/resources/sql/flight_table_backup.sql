CREATE TABLE IF NOT EXISTS flight (
  id INT NOT NULL AUTO_INCREMENT UNIQUE,
  name VARCHAR(255),
  carrier VARCHAR(255),
  duration TIME, 
  meal_on BOOLEAN DEFAULT FALSE,
  fare DECIMAL,
  departure_date DATETIME,
  arrival_date DATETIME,
);
