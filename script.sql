
DROP SCHEMA IF EXISTS meublos;
CREATE SCHEMA meublos DEFAULT CHARSET = utf8mb4;
USE meublos;



CREATE TABLE `Matiere` (
	nomMatiere VARCHAR(50) NOT NULL,
	
	CONSTRAINT PK_Matiere PRIMARY KEY (nomMatiere)
) ENGINE = InnoDB;


CREATE TABLE `Style` (
	nomStyle VARCHAR(50) NOT NULL,
	
	CONSTRAINT PK_Style PRIMARY KEY (nomStyle)
) ENGINE = InnoDB;



CREATE TABLE `Meuble` (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	nom VARCHAR(50) NOT NULL,
	description TEXT NOT NULL,
	prixVente DECIMAL(6,2) NOT NULL,
	matiere VARCHAR(50) NOT NULL,
	style VARCHAR(50),
	
	INDEX IDX_FK_Meuble_Matiere (matiere),
	INDEX IDX_FK_Meuble_Style (style),
	
	CONSTRAINT PK_Meuble PRIMARY KEY (id),
	
	CONSTRAINT FK_Meuble_Matiere
		FOREIGN KEY (matiere)
		REFERENCES Matiere (nomMatiere)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Meuble_Style
		FOREIGN KEY (style)
		REFERENCES Style (nomStyle)
		ON UPDATE CASCADE
) ENGINE = InnoDB;





    