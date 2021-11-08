
DROP SCHEMA IF EXISTS meublos;
CREATE SCHEMA meublos DEFAULT CHARSET = utf8mb4;
USE meublos;



CREATE TABLE `Categorie` (
	nomCategorie VARCHAR(50) NOT NULL,
	
	CONSTRAINT PK_Categorie PRIMARY KEY (nomCategorie)
) ENGINE = InnoDB;



CREATE TABLE `Meuble` (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	nom VARCHAR(50) NOT NULL,
	description TEXT NOT NULL,
	prixVente DECIMAL(6,2) NOT NULL,
	quantite INT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_Meuble PRIMARY KEY (id)
	
) ENGINE = InnoDB;


CREATE TABLE `Panier` (
	idUser INT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_Panier PRIMARY KEY (idUser)
) ENGINE = InnoDB;


CREATE TABLE `Meuble_Categorie` (
	idMeuble INT UNSIGNED NOT NULL,
	nomCategorie VARCHAR(50) NOT NULL,
	
	CONSTRAINT PK_Meuble_Categorie PRIMARY KEY (idMeuble, nomCategorie),
	
	CONSTRAINT FK_Meuble_Categorie_nomCategorie FOREIGN KEY (nomCategorie)
		REFERENCES Categorie (nomCategorie)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
		
	CONSTRAINT FK_Meuble_Categorie_idMeuble FOREIGN KEY (idMeuble)
		REFERENCES Meuble (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE `Panier_Meuble` (
	idMeuble INT UNSIGNED NOT NULL,
	idUserPanier INT UNSIGNED NOT NULL,
	quantite INT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_Panier_Meuble PRIMARY KEY (idMeuble, idUserPanier),
	
	CONSTRAINT FK_Panier_Meuble_idMeuble FOREIGN KEY (idMeuble)
		REFERENCES Meuble (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
		
	CONSTRAINT FK_Panier_Meuble_idUserPanier FOREIGN KEY (idUserPanier)
		REFERENCES Panier (idUser)
		ON UPDATE CASCADE
		ON DELETE CASCADE
) ENGINE = INNODB;





INSERT INTO `categorie` (`nomCategorie`) VALUES
	('Armoire'),
	('Louis XVI'),
	('Table');
	
INSERT INTO `meuble` (`id`, `nom`, `description`, `prixVente`, `quantite`) VALUES
	(1, 'Un meuble du grenier', 'Il était dans mon grenier pendant des années', 10.55, 4),
	(2, 'Table en marbre', 'En super état ! Comme neuf. \r\n\r\nPersonne non sérieuse s\'abstenir.', 430.00, 2),
	(3, 'Armoire style "Louis XVI"', 'Petite pépite avec quelques dégats presque pas visible\r\nMerci de me contacter pour plus d\'informations.', 249.90, 1);

INSERT INTO `meuble_categorie` (`idMeuble`, `nomCategorie`) VALUES
	(3, 'Armoire'),
	(3, 'Louis XVI'),
	(2, 'Table');
	
INSERT INTO `panier` (`idUser`) VALUES
	(1);
	
	
INSERT INTO `panier_meuble` (`idMeuble`, `idUserPanier`, `quantite`) VALUES
	(1, 1, 1),
	(2, 1, 2);