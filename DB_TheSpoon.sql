DROP DATABASE thespoon;
CREATE DATABASE thespoon;
use thespoon;

CREATE TABLE Ristoratore (
Id INT primary key not null auto_increment,
Password VARCHAR(30) not null,
Nome VARCHAR(20) not null,
Cognome VARCHAR(20) not null,
Email VARCHAR(20) not null,
Telefono VARCHAR(20) not null,
Data_Nascita date not null,
Genere CHAR(1)
);

CREATE TABLE Ristorante ( 
IdRistorante INT(10) primary key not null auto_increment,
Nome VARCHAR(50) not null,
N_Civico VARCHAR(3) not null,
Cap INT(5) not null,
Via VARCHAR(100) not null,
Provincia VARCHAR(2) not null,
Telefono VARCHAR(11) not null
);

CREATE TABLE Prenotazione ( 
IdPrenotazione INT(10) primary key not null auto_increment,
Data DATE not null,
Ora TIME not null,
Nr_Persone INT(2) not null,
Email VARCHAR(100) not null,
Cellulare INT(11) not null
);

CREATE TABLE Tavolo ( 
NumeroTavolo INT(2) primary key not null,
Stato BOOLEAN not null,
Capacita INT(2) not null
);

CREATE TABLE PrenotazioneTavolo (
NumeroTavolo INT(2),
IdPrenotazione INT(10),
FOREIGN KEY (NumeroTavolo) REFERENCES Tavolo(NumeroTavolo),
FOREIGN KEY (IdPrenotazione) REFERENCES Prenotazione(IdPrenotazione)
);


CREATE TABLE Ordine (  
IdOrdine INT(10) primary key not null,
Tipologia BOOLEAN not null,
Ora TIME,
Nr_Tavolo INT(2),
Totale DECIMAL(10,2) not null
);

CREATE TABLE Menu ( 
IdMenu INT(10) primary key not null auto_increment,
Nome CHAR(30) not null,
Categoria CHAR(30) not null
);

CREATE TABLE Prodotto (
IdProdotto INT primary key not null auto_increment,
Nome CHAR(30) not null,
Descrizione VARCHAR(2000) not null,
Prezzo DECIMAL(10,2) not null
);

CREATE TABLE MenuProdotto (
IdProdotto int not null,
IdMenu int not null,
foreign key (IdProdotto) references prodotto(IdProdotto),
foreign key(IdMenu) references Menu(IdMenu)
);

