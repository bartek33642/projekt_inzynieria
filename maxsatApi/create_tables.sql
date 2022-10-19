-- Zones definition

CREATE TABLE IF NOT EXISTS Zones (
                                     zoneId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                                     cordX INTEGER,
                                     cordY INTEGER,
                                     demandFactor REAL,
                                     accessibilityFactor REAL,
                                     attractivenessFactor REAL
);


-- ParkingLots definition

CREATE TABLE IF NOT EXISTS ParkingLots (
                                           parkingLotId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                                           zoneId INTEGER NOT NULL,
                                           haveSpaceForHandicapped INTEGER,
                                           isGuarded INTEGER,
                                           isPaid INTEGER,
                                           freeSpaces INTEGER,
                                           isPrivate INTEGER,
                                           haveSpacesForElectrics INTEGER,
                                           CONSTRAINT ParkingLot_FK FOREIGN KEY (zoneId) REFERENCES Zones(zoneId) ON DELETE CASCADE ON UPDATE CASCADE
);