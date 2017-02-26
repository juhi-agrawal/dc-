-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 08, 2016 at 08:57 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `peertopeer`
--

-- --------------------------------------------------------

--
-- Table structure for table `filelist`
--

CREATE TABLE IF NOT EXISTS `filelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `filename` varchar(50) NOT NULL,
  `ip` varchar(100) NOT NULL,
  `location` varchar(250) NOT NULL,
  `about` varchar(100) NOT NULL,
  `filesize` bigint(11) NOT NULL,
  `format` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `filelist`
--

INSERT INTO `filelist` (`id`, `username`, `filename`, `ip`, `location`, `about`, `filesize`, `format`) VALUES
(1, 'abc', 'a.txt', '', '', '', 0, ''),
(2, 'abcd', 'a.txt', '', '', '', 0, ''),
(3, '', '', '', '', '', 0, ''),
(4, 'name', 'filename', 'ip', 'path', 'about', 74924, 'ext'),
(5, 'muskan', 'c.pdf', '/127.0.0.1:53657', 'C:UsersJUHI AGRAWALDocumentsComputer_Science_and_Engineering.pdf', '', 433559, '.pdf'),
(6, 'list_programs2016.docx', 'list_programs2016.docx', '/127.0.0.1:51306', 'C:UsersJUHI AGRAWALDocumentslist_programs2016.docx', '', 23365, '.docx'),
(7, '3-mistake-of-my-life.pdf', '3-mistake-of-my-life.pdf', '/192.168.122.54:55872', 'C:UserspriyankaDocuments\novels3-mistake-of-my-life.pdf', '', 1938700, '.pdf'),
(8, 'hweu', 'Registration Forms.htm', '/127.0.0.1:50226', 'C:UsersJUHI AGRAWALDocumentsRegistration Forms.htm', '', 4761, '.htm');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
