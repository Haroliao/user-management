CREATE DATABASE UserLists;
USE UserLists;

CREATE TABLE Users(
Username VARCHAR(50) NOT NULL PRIMARY KEY,
Name VARCHAR(50) NOT NULL,
PhoneNum VARCHAR(15) NOT NULL,
PhoneNumLocation VARCHAR(50) NOT NULL,
RegistrationDATE DATE NOT NULL,
Organization VARCHAR(15) NOT NULL,
Role VARCHAR(50) NOT NULL
);
