CREATE TABLE IF NOT EXISTS Speler
(
    id 					INT AUTO_INCREMENT,
    voornaam            VARCHAR(20),
    familienaam         VARCHAR(30),
    gebruikersnaam      VARCHAR(40) NOT NULL,
    isAdmin            	BOOLEAN NOT NULL,
    wachtwoord          VARCHAR(256) NOT NULL,
    CONSTRAINT PK_Speler PRIMARY KEY(id),
    UNIQUE (gebruikersnaam)
);

CREATE TABLE IF NOT EXISTS Spel
(
    id			INT AUTO_INCREMENT,
    naam		VARCHAR(100),
    spelerID	INT NOT NULL,
    CONSTRAINT PK_Spel PRIMARY KEY(id),
    UNIQUE (naam),
    CONSTRAINT FK_Spel_Speler FOREIGN KEY (spelerID) REFERENCES Speler(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Spelbord
(
    id			INT AUTO_INCREMENT,
    volgnummer  INT NOT NULL,
    spelID		INT NOT NULL,
    CONSTRAINT PK_Spelbord PRIMARY KEY(id),
    -- UNIQUE (bord),
    CONSTRAINT FK_Spelbord_Spel FOREIGN KEY (spelID) REFERENCES Spel(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Veld
(
    x				INT,
    y				INT,
    teken			VARCHAR(1),
    spelbordID		INT NOT NUll,
    CONSTRAINT PK_Veld PRIMARY KEY(x, y, spelbordID),
    CONSTRAINT FK_Veld_Spelbord FOREIGN KEY (spelbordID) REFERENCES Spelbord(id)
    ON DELETE CASCADE
);