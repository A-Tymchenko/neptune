DROP TABLE IF EXISTS ADVERTISEMENT;
CREATE TABLE ADVERTISEMENT (
AD_ID LONG PRIMARY KEY AUTO_INCREMENT,
TITLE VARCHAR(255),
CONTEXT VARCHAR(255),
IMAGE_URL VARCHAR(255),
LANGUAGE VARCHAR(255),
);