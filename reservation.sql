-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for reservation
CREATE DATABASE IF NOT EXISTS `reservation` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `reservation`;

-- Dumping structure for table reservation.campo
CREATE TABLE IF NOT EXISTS `campo` (
  `numero` tinyint(4) NOT NULL,
  `superficie` varchar(50) NOT NULL,
  `sport` int(11) NOT NULL DEFAULT 0,
  `ore_disponibili` int(11) NOT NULL DEFAULT 0,
  `inizio_ore_prenotabili` date DEFAULT NULL,
  `fine_ore_prenotabili` date DEFAULT NULL,
  PRIMARY KEY (`numero`),
  KEY `FK_campo_sport` (`sport`),
  CONSTRAINT `FK_campo_sport` FOREIGN KEY (`sport`) REFERENCES `sport` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.campo: ~3 rows (approximately)
/*!40000 ALTER TABLE `campo` DISABLE KEYS */;
INSERT INTO `campo` (`numero`, `superficie`, `sport`, `ore_disponibili`, `inizio_ore_prenotabili`, `fine_ore_prenotabili`) VALUES
	(1, 'Terra Battuta', 1, 0, NULL, NULL),
	(2, 'Terra Battuta', 1, 0, NULL, NULL),
	(3, 'Erba Sintetica', 2, 0, NULL, NULL);
/*!40000 ALTER TABLE `campo` ENABLE KEYS */;

-- Dumping structure for table reservation.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `data_nascita` date NOT NULL,
  `luogo_nascita` varchar(50) NOT NULL DEFAULT '',
  `codice_fiscale` varchar(16) NOT NULL,
  `nazionalita` varchar(16) NOT NULL,
  `scadenza_certificato` date NOT NULL,
  `indirizzo` varchar(50) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL,
  `num_telefono` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `num_telefono` (`num_telefono`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `codice_fiscale` (`codice_fiscale`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.cliente: ~11 rows (approximately)
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`id`, `nome`, `cognome`, `data_nascita`, `luogo_nascita`, `codice_fiscale`, `nazionalita`, `scadenza_certificato`, `indirizzo`, `email`, `num_telefono`) VALUES
	(9, 'Bernardino', 'Di Biagio', '1992-08-06', 'Orvieto', 'brn001', 'italiana', '2023-02-12', 'via Alessandro Guarnelli 22', 'bernardinodibiagio@live.it', '3393517768'),
	(11, 'Riccardo', 'Bagaglini', '1992-10-06', 'Roma', '001BGR', 'italiana', '2022-09-28', 'via della Huelva, 69 Velletri(RM)', 'bagaric@gmail.com', '3280000000'),
	(12, 'Alessio', 'Ciolfi', '1990-07-07', 'Roma', '001CIO', 'italiana', '2022-12-05', 'via Casilina 33, Roma(RM)', 'alecio@gmail.com', '3480000000'),
	(16, 'Christian', 'Pezzotta', '1999-09-21', 'Roma', '001PZZ', 'italiana', '2022-09-01', 'via della Meloria, 68', 'crzpzz@live.it', '3330202020'),
	(18, 'Simone', 'Antonelli', '1996-09-20', 'Roma', '001NNT', 'italiana', '2022-09-29', 'via di Tor Pignattara, 69', 'simanto@live.it', '3420000001'),
	(20, 'Davide', 'Crescenzi', '1993-09-20', 'Roma', '001CRZ', 'italiana', '2022-09-28', 'via di Dublino,01', 'davicresc@libero.it', '3410000000'),
	(29, 'Test', 'Cliente', '2005-12-01', 'test', '000000', 'test', '2023-01-01', 'indirizzo test', 'test@test.com', '0000000000'),
	(30, 'Test2', 'Cliente2', '1970-01-01', 'test2', '000001', 'test2', '2023-01-01', 'indirizzo test2', 'test2@test.com', '0000000001'),
	(31, 'Test3', 'Cliente3', '1970-01-01', 'test3', '000002', 'test3', '2023-01-01', 'indirizzo test3', 'test3@test.com', '0000000002'),
	(32, 'Test4', 'Cliente4', '1970-01-01', 'test4', '000003', 'test4', '2023-01-01', 'indirizzo test4', 'test4@test.com', '0000000003'),
	(33, 'Test5', 'Cliente5', '1970-01-01', 'test5', '000004', 'test5', '2023-01-01', 'indirizzo test5', 'test5@test.com', '0000000004');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;

-- Dumping structure for table reservation.integrazione_tessera
CREATE TABLE IF NOT EXISTS `integrazione_tessera` (
  `id` int(11) NOT NULL,
  `tipo_integrazione` varchar(50) DEFAULT NULL,
  `prezzo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.integrazione_tessera: ~3 rows (approximately)
/*!40000 ALTER TABLE `integrazione_tessera` DISABLE KEYS */;
INSERT INTO `integrazione_tessera` (`id`, `tipo_integrazione`, `prezzo`) VALUES
	(1, 'FIT AGO', 30),
	(2, 'FIT NON AGO', 15),
	(3, 'CSEN', 3);
/*!40000 ALTER TABLE `integrazione_tessera` ENABLE KEYS */;

-- Dumping structure for table reservation.prenotazione
CREATE TABLE IF NOT EXISTS `prenotazione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `ora_inizio` time NOT NULL,
  `ora_fine` time NOT NULL,
  `modalita` varchar(50) NOT NULL DEFAULT '0',
  `cliente` int(11) NOT NULL,
  `campo` tinyint(4) NOT NULL DEFAULT 0,
  `sport` int(11) NOT NULL DEFAULT 0,
  `giocatore1` varchar(50) NOT NULL,
  `giocatore2` varchar(50) NOT NULL,
  `giocatore3` varchar(50) DEFAULT NULL,
  `giocatore4` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_prenotazione_campo` (`campo`),
  KEY `FK_prenotazione_cliente` (`cliente`) USING BTREE,
  KEY `FK_prenotazione_sport` (`sport`),
  CONSTRAINT `FK_prenotazione_campo` FOREIGN KEY (`campo`) REFERENCES `campo` (`numero`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_prenotazione_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_prenotazione_sport` FOREIGN KEY (`sport`) REFERENCES `sport` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.prenotazione: ~0 rows (approximately)
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;

-- Dumping structure for table reservation.sport
CREATE TABLE IF NOT EXISTS `sport` (
  `id` int(11) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.sport: ~2 rows (approximately)
/*!40000 ALTER TABLE `sport` DISABLE KEYS */;
INSERT INTO `sport` (`id`, `tipo`) VALUES
	(1, 'TENNIS'),
	(2, 'PADEL');
/*!40000 ALTER TABLE `sport` ENABLE KEYS */;

-- Dumping structure for table reservation.tesseramento
CREATE TABLE IF NOT EXISTS `tesseramento` (
  `cliente` int(11) DEFAULT NULL,
  `tipo_tessera` int(11) DEFAULT NULL,
  `integrazione_tessera` int(11) DEFAULT NULL,
  `codice_tessera` int(11) NOT NULL AUTO_INCREMENT,
  `data_tesseramento` timestamp NOT NULL DEFAULT curdate(),
  PRIMARY KEY (`codice_tessera`),
  UNIQUE KEY `cliente` (`cliente`),
  KEY `FK_tesseramento_tipo_tessera` (`tipo_tessera`) USING BTREE,
  KEY `FK_tesseramento_integrazione_tessera` (`integrazione_tessera`),
  CONSTRAINT `FK_tesseramento_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tesseramento_integrazione_tessera` FOREIGN KEY (`integrazione_tessera`) REFERENCES `integrazione_tessera` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tesseramento_tipo_tessera` FOREIGN KEY (`tipo_tessera`) REFERENCES `tipo_tessera` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.tesseramento: ~0 rows (approximately)
/*!40000 ALTER TABLE `tesseramento` DISABLE KEYS */;
INSERT INTO `tesseramento` (`cliente`, `tipo_tessera`, `integrazione_tessera`, `codice_tessera`, `data_tesseramento`) VALUES
	(9, 1, 3, 1000, '2022-12-02 18:02:23');
/*!40000 ALTER TABLE `tesseramento` ENABLE KEYS */;

-- Dumping structure for table reservation.tipo_tessera
CREATE TABLE IF NOT EXISTS `tipo_tessera` (
  `id` int(11) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `prezzo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table reservation.tipo_tessera: ~3 rows (approximately)
/*!40000 ALTER TABLE `tipo_tessera` DISABLE KEYS */;
INSERT INTO `tipo_tessera` (`id`, `tipo`, `prezzo`) VALUES
	(1, 'SUPER-TENNIS', 35),
	(2, 'SUPER-PADEL', 35),
	(3, 'BASE-QUOTA ASSOCIATIVA', 15);
/*!40000 ALTER TABLE `tipo_tessera` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
