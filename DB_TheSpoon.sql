DROP DATABASE thespoon;
CREATE DATABASE thespoon;
use thespoon;

CREATE TABLE Ristoratore (
Id INT primary key not null auto_increment,
Password VARCHAR(30) not null,
Nome VARCHAR(20) not null,
Cognome VARCHAR(20) not null,
Email VARCHAR(30) not null,
Telefono VARCHAR(20) not null,
Data_Nascita date not null
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
Cellulare VARCHAR(11) not null
);

CREATE TABLE Tavolo ( 
NumeroTavolo VARCHAR(2) primary key not null,
Stato BOOLEAN not null,
Capacita INT(2) not null
);

CREATE TABLE PrenotazioneTavolo (
IdPrenotazione INT(10),
NumeroTavolo VARCHAR(2),
FOREIGN KEY (NumeroTavolo) REFERENCES Tavolo(NumeroTavolo),
FOREIGN KEY (IdPrenotazione) REFERENCES Prenotazione(IdPrenotazione)
);

CREATE TABLE Ordine (  
IdOrdine INT(10) primary key not null,
Tipologia BOOLEAN not null,
Ora TIME,
Nr_Tavolo CHAR(2),
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

-- Inserimento di valori casuali nella tabella Ristoratore
INSERT INTO Ristoratore (Password, Nome, Cognome, Email, Telefono, Data_Nascita) VALUES
  ('P@ssw0rd123', 'Alice', 'Smith', 'alice.smith@example.com', '+1234567890', '1990-03-15'),
  ('SecurePwd!567', 'Bob', 'Johnson', 'bob.johnson@example.com', '+1987654321', '1985-07-22'),
  ('RandomPwd2023', 'Claire', 'Brown', 'claire.brown@example.com', '+1654321897', '1992-11-05'),
  ('MySecret123', 'David', 'Davis', 'david.davis@example.com', '+1765432980', '1988-04-30'),
  ('Str0ngP@ss', 'Emma', 'Patel', 'emma.patel@example.com', '+1876543210', '1995-09-12');

-- Inserimento di valori casuali nella tabella Ristorante
INSERT INTO Ristorante (Nome, N_Civico, Cap, Via, Provincia, Telefono) VALUES
  ('Trattoria La Bella Vita', '123', 12345, 'Via Roma', 'RM', '01234567890'),
  ('Osteria del Gusto', '456', 67890, 'Via Milano', 'MI', '09876543210'),
  ('Pizzeria La Vecchia Napoli', '789', 54321, 'Via Napoli', 'NA', '07654321098'),
  ('Ristorante Al canto del Mare', '101', 98765, 'Via Firenze', 'FI', '08976543210'),
  ('Locanda dei Sapori', '112', 54321, 'Via Torino', 'TO', '06543210987');
  
  -- Inserimento di valori casuali nella tabella Prenotazione
INSERT INTO Prenotazione (Data, Ora, Nr_Persone, Email, Cellulare) VALUES 
  ('2023-01-15', '19:30:00', 3, 'cliente1@example.com', '12345678999'),
  ('2023-02-20', '20:00:00', 5, 'cliente2@example.com', '12345678999'),
  ('2023-03-10', '19:45:00', 2, 'cliente3@example.com', '12345678999'),
  ('2023-04-05', '20:45:00', 4, 'cliente4@example.com', '12345678999'),
  ('2023-05-12', '21:30:00', 6, 'cliente5@example.com', '12345678999');


-- Inserimento di valori casuali nella tabella Tavolo
INSERT INTO Tavolo (NumeroTavolo, Stato, Capacita) VALUES
(1, TRUE, 4),
(2, TRUE, 6),
(3, TRUE, 2),
(4, TRUE, 3),
(5, TRUE, 5);

-- Inserimento di valori casuali nella tabella PrenotazioneTavolo
INSERT INTO PrenotazioneTavolo (IdPrenotazione, NumeroTavolo) VALUES
(1, '4'),
(2, '5'),
(3, '3'),
(4, '1'),
(5, '2');


-- Inserimento di valori casuali nella tabella Ordine (Tipologia 0: Ordine al tavolo, Tipologia 1: Ordine da asporto)
INSERT INTO Ordine (IdOrdine, Tipologia, Ora, Nr_Tavolo, Totale) VALUES
  (1, 1, '12:30:00', '', 45.75),
  (2, 0, '', 3, 28.50),
  (3, 1, '20:15:00', '', 62.20),
  (4, 0, '', 1, 35.90),
  (5, 1, '19:30:00', '', 50.00);

-- Inserimento di valori casuali nella tabella Menù
INSERT INTO Menu (Nome, Categoria) VALUES 
	('Mare', 'Alla carta'),
    ('Terra', 'Alla carta'),
    ('Pizza', 'All You Can Eat');
    
-- Inserimento di valori casuali nella tabella Prodotto
INSERT INTO Prodotto (Nome, Descrizione, Prezzo) VALUES
  ('Spaghetti alle Vongole', 'Spaghetti con vongole fresche, aglio e prezzemolo', 18.99),
  ('Grigliata di Pesce', 'Selezione di pesce grigliato con contorno di verdure', 29.99),
  ('Risotto ai Frutti di Mare', 'Risotto con frutti di mare misti e pomodorini', 22.50),
  ('Calamari Fritti', 'Calamari freschi fritti serviti con salsa agli agrumi', 14.99),
  ('Zuppa di Pesce', 'Zuppa ricca di pesce misto con pomodoro e aromi mediterranei', 25.99),
  ('Insalata di Gamberi', 'Insalata mista con gamberi grigliati e vinaigrette al limone', 16.50),
  ('Filetto di Salmone', 'Filetto di salmone al forno con salsa alle erbe', 26.99),
  ('Ravioli di Mare', 'Ravioli ripieni di ricotta e frutti di mare, conditi con burro e salvia', 19.99),
  ('Carpaccio di Tonno', 'Fette sottili di tonno fresco marinato con olio e limone', 21.99),
  ('Polpo alla Griglia', 'Polpo grigliato con patate e olive nere', 32.99),
  ('Bistecca di Manzo', 'Bistecca di manzo alla griglia con contorno di patate arrosto', 25.99),
  ('Pollo alle Erbe', 'Petto di pollo marinato alle erbe con verdure al vapore', 18.50),
  ('Tagliatelle al Ragu', 'Tagliatelle fatte in casa con ragu di carne tradizionale', 14.99),
  ('Costolette di Agnello', 'Costolette di agnello alla brace con purea di piselli', 29.99),
  ('Hamburger Gourmet', 'Hamburger di manzo gourmet con formaggio cheddar e bacon', 12.99),
  ('Pappardelle al Cinghiale', 'Pappardelle con sugo di cinghiale e funghi porcini', 22.50),
  ('Ossobuco', 'Ossobuco di vitello cotto lentamente con risotto alla milanese', 32.99),
  ('Salsiccia Grigliata', 'Salsiccia italiana grigliata con peperoni e cipolle', 16.50),
  ('Filetto di Maiale', 'Filetto di maiale avvolto in pancetta con salsa di mirtilli rossi', 19.99),
  ('Risotto ai Funghi', 'Risotto cremoso con funghi porcini e prezzemolo fresco', 15.99),
  ('Margherita','Pomodoro fresco, mozzarella di bufala e basilico.',9.99),
  ('Pepperoni','Pomodoro, mozzarella, pepperoni piccanti.',10.99),
  ('Prosciutto e Funghi',' Mozzarella, olio di oliva, funghi freschi.',11.50),
  ('Quattro Formaggi','Mozzarella, gorgonzola, parmigiano e pecorino',12.99),
  ('Vegetariana','Pomodoro, mozzarella, pepperoni, cipolle, funghi, olive.',11.99),
  ('Diavola','Pomodoro, mozzarella, salsiccia piccante, peperonicini.',11.50),
  ('Capricciosa','Pomodoro, mozzarella, prosciutto cotto, carciofi, olive, funghi.',12.50),
  ('Calzone Ripieno','Prosciutto cotto, ricotta, spinaci e mozzarella.',13.50),
  ('Quattro Stagioni','Pomodoro, mozzarella, carciofi, funghi, prosciutto cotto, olive',13.99),
  ('Mortazza','Mozzarella, crema di tartufo, funghi, parmigiano.',14.99);
  
  INSERT INTO MenuProdotto (IdProdotto, IdMenu) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 1),
  (5, 1),
  (6, 1),
  (7, 1),
  (8, 1),
  (9, 1),
  (10, 1),
  (11, 2),
  (12, 2),
  (13, 2),
  (14, 2),
  (15, 2),
  (16, 2),
  (17, 2),
  (18, 2),
  (19, 2),
  (20, 2),
(21, 3),
(22, 3),
(23, 3),
(24, 3),
(25, 3),
(26, 3),
(27, 3),
(28, 3),
(29, 3),
(30, 3);  


    









  


