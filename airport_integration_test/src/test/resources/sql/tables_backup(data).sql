--AIRPORT
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('Kenedy','12345','international','New York',10);
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('Changy','54367','international','Singapur',15);
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('Shiphol','32456','international','Shanhai',11);
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('Dallas/fort-uert','86345','international','Dallas',10);
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('Sharl-de-goll','14562','international','Paris',8);
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('Hitrou','32674','international','London',9);
INSERT INTO AIRPORT (APNAME, APNUM, APTYPE, ADDRESSES, TERMINALCOUNT) VALUES ('O-hara','83257','international','Chicago',7);

--FLIGHT
INSERT INTO flight (name,carrier,duration,meal_on, fare,departure_date,arrival_date) VALUES (
'Kyiv-Rome', 'Wizz Air', '02:00:00', true, 100, '2018-06-17 13:15:00', '2018-06-17 15:16:00');

--PLANE
INSERT INTO plane (owner,model,type, plateNumber) VALUES ('MAU', 'Boeing', 'LargeCarrier', 13249);

--TICKET
INSERT INTO PUBLIC.TICKET (TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE)
  VALUES ('A123-456F', 'Petro Velykyi', 'AA192939', '2018-06-21 21:05:00');
INSERT INTO PUBLIC.TICKET (TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE)
  VALUES ('A123-456E', 'Olga Tymofeeva', 'AA101010', '2018-06-21 21:25:00');
INSERT INTO PUBLIC.TICKET (TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE)
  VALUES ('123456', 'John Dow', 'QW198374', null);
