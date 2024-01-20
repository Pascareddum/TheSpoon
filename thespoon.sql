DROP DATABASE IF EXISTS thespoon;
CREATE DATABASE thespoon;
use thespoon;

-- thespoon.prodotto definition

CREATE TABLE `prodotto` (
  `id_prodotto` int NOT NULL AUTO_INCREMENT,
  `Nome` char(30) NOT NULL,
  `Descrizione` varchar(2000) NOT NULL,
  `Prezzo` decimal(10,2) NOT NULL,
  `idristorante` int NOT NULL,
  PRIMARY KEY (`id_prodotto`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.ristorante definition

CREATE TABLE `ristorante` (
  `id_ristorante` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `N_Civico` varchar(3) NOT NULL,
  `Cap` int NOT NULL,
  `Via` varchar(100) NOT NULL,
  `Provincia` varchar(2) NOT NULL,
  `Telefono` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_ristorante`),
  FULLTEXT KEY `ristorante_Nome_IDX` (`Nome`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.ristoratore definition

CREATE TABLE `ristoratore` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `Password` binary(60) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `Email` varchar(20) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Data_Nascita` date NOT NULL,
  `Genere` char(1) DEFAULT NULL,
  `role` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_unique` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.menu definition

CREATE TABLE `menu` (
  `id_menu` int NOT NULL AUTO_INCREMENT,
  `Nome` char(30) NOT NULL,
  `Categoria` char(30) NOT NULL,
  `fk_id_ristorante` int NOT NULL,
  PRIMARY KEY (`id_menu`),
  KEY `menu_ristorante_FK` (`fk_id_ristorante`),
  CONSTRAINT `menu_ristorante_FK` FOREIGN KEY (`fk_id_ristorante`) REFERENCES `ristorante` (`id_ristorante`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.menuprodotto definition

CREATE TABLE `menuprodotto` (
  `id_prodotto` int NOT NULL,
  `id_menu` int NOT NULL,
  UNIQUE KEY `menuprodotto_unique` (`id_prodotto`,`id_menu`),
  KEY `IdMenu` (`id_menu`),
  CONSTRAINT `menuprodotto_ibfk_1` FOREIGN KEY (`id_prodotto`) REFERENCES `prodotto` (`id_prodotto`),
  CONSTRAINT `menuprodotto_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.ordine definition

CREATE TABLE `ordine` (
  `IdRistorante` int NOT NULL,
  `IdOrdine` int NOT NULL AUTO_INCREMENT,
  `Tipologia` tinyint(1) NOT NULL,
  `Ora` time DEFAULT NULL,
  `Nr_Tavolo` char(2) DEFAULT NULL,
  `Quantita` int DEFAULT NULL,
  `Totale` decimal(10,2) NOT NULL,
  `ChatId` int NOT NULL,
  `Stato` tinyint(1) NOT NULL,
  PRIMARY KEY (`IdOrdine`),
  KEY `IdRistorante` (`IdRistorante`),
  CONSTRAINT `ordine_ibfk_1` FOREIGN KEY (`IdRistorante`) REFERENCES `ristorante` (`id_ristorante`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.possiede definition

CREATE TABLE `possiede` (
  `id_ristorante` int NOT NULL,
  `id` int unsigned NOT NULL,
  KEY `IdRistorante` (`id_ristorante`),
  KEY `Id` (`id`),
  CONSTRAINT `possiede_ibfk_1` FOREIGN KEY (`id_ristorante`) REFERENCES `ristorante` (`id_ristorante`),
  CONSTRAINT `possiede_ibfk_2` FOREIGN KEY (`id`) REFERENCES `ristoratore` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.prenotazione definition

CREATE TABLE `prenotazione` (
  `IdPrenotazione` int NOT NULL AUTO_INCREMENT,
  `Data` date NOT NULL,
  `Ora` time NOT NULL,
  `Nr_Persone` int NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Cellulare` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `chatid` int NOT NULL,
  `IdRistorante` int NOT NULL,
  PRIMARY KEY (`IdPrenotazione`),
  KEY `prenotazione_ristorante_FK` (`IdRistorante`),
  CONSTRAINT `prenotazione_ristorante_FK` FOREIGN KEY (`IdRistorante`) REFERENCES `ristorante` (`id_ristorante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.prodotto_ordine definition

CREATE TABLE `prodotto_ordine` (
  `id_prodotto` int NOT NULL,
  `idOrdine` int NOT NULL,
  `Quantita` int NOT NULL,
  KEY `prodotto_ordine_prodotto_FK` (`id_prodotto`),
  KEY `prodotto_ordine_ordine_FK` (`idOrdine`),
  CONSTRAINT `prodotto_ordine_ordine_FK` FOREIGN KEY (`idOrdine`) REFERENCES `ordine` (`IdOrdine`),
  CONSTRAINT `prodotto_ordine_prodotto_FK` FOREIGN KEY (`id_prodotto`) REFERENCES `prodotto` (`id_prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.tavolo definition

CREATE TABLE `tavolo` (
  `NumeroTavolo` varchar(2) NOT NULL,
  `Stato` tinyint(1) NOT NULL,
  `Capacita` int NOT NULL,
  `id_ristorante` int NOT NULL,
  PRIMARY KEY (`NumeroTavolo`),
  KEY `tavolo_ristorante_FK` (`id_ristorante`),
  CONSTRAINT `tavolo_ristorante_FK` FOREIGN KEY (`id_ristorante`) REFERENCES `ristorante` (`id_ristorante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.prenota definition

CREATE TABLE `prenota` (
  `IdPrenotazione` int NOT NULL,
  `IdRistorante` int NOT NULL,
  KEY `IdPrenotazione` (`IdPrenotazione`),
  KEY `IdRistorante` (`IdRistorante`),
  CONSTRAINT `prenota_ibfk_1` FOREIGN KEY (`IdPrenotazione`) REFERENCES `prenotazione` (`IdPrenotazione`),
  CONSTRAINT `prenota_ibfk_2` FOREIGN KEY (`IdRistorante`) REFERENCES `ristorante` (`id_ristorante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- thespoon.prenotazionetavolo definition

CREATE TABLE `prenotazionetavolo` (
  `IdPrenotazione` int DEFAULT NULL,
  `NumeroTavolo` varchar(2) DEFAULT NULL,
  KEY `IdPrenotazione` (`IdPrenotazione`),
  KEY `NumeroTavolo` (`NumeroTavolo`),
  CONSTRAINT `prenotazionetavolo_ibfk_2` FOREIGN KEY (`IdPrenotazione`) REFERENCES `prenotazione` (`IdPrenotazione`),
  CONSTRAINT `prenotazionetavolo_ibfk_3` FOREIGN KEY (`NumeroTavolo`) REFERENCES `tavolo` (`NumeroTavolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;