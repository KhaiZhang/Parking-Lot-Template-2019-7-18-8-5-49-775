CREATE TABLE Parking_Lot
(
id int AUTO_INCREMENT PRIMARY KEY,
name varchar(255) UNIQUE,
capacity int CHECK (capacity>0),
location varchar(255)
);
