-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 23, 2019 at 07:14 AM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 5.6.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `crds`
--

-- --------------------------------------------------------

--
-- Table structure for table `birth`
--

CREATE TABLE `birth` (
  `birth_id` int(11) NOT NULL,
  `page_no` varchar(20) NOT NULL,
  `book_no` varchar(20) NOT NULL,
  `reg_no` varchar(20) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `middle_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `reg_date` text NOT NULL,
  `birth_date` text NOT NULL,
  `birth_place` text NOT NULL,
  `gender` varchar(20) NOT NULL,
  `nationality` varchar(30) NOT NULL,
  `father_name` text NOT NULL,
  `father_nationality` text NOT NULL,
  `mother_name` text NOT NULL,
  `mother_nationality` varchar(30) NOT NULL,
  `parent_marriage_date` text NOT NULL,
  `parent_marriage_place` text NOT NULL,
  `remarks` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `death`
--

CREATE TABLE `death` (
  `death_id` int(11) NOT NULL,
  `page_no` varchar(30) NOT NULL,
  `book_no` varchar(30) NOT NULL,
  `reg_no` varchar(30) NOT NULL,
  `reg_date` text NOT NULL,
  `first_name` text NOT NULL,
  `middle_name` text NOT NULL,
  `last_name` text NOT NULL,
  `gender` varchar(20) NOT NULL,
  `age` varchar(30) NOT NULL,
  `civil_status` text NOT NULL,
  `citizenship` text NOT NULL,
  `death_date` text NOT NULL,
  `death_place` text NOT NULL,
  `death_cause` text NOT NULL,
  `remarks` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `marriage`
--

CREATE TABLE `marriage` (
  `marriage_id` int(11) NOT NULL,
  `page_no` varchar(20) NOT NULL,
  `book_no` varchar(20) NOT NULL,
  `reg_no` varchar(20) NOT NULL,
  `husband_first_name` varchar(30) NOT NULL,
  `husband_middle_name` varchar(30) NOT NULL,
  `husband_last_name` varchar(30) NOT NULL,
  `husband_age` varchar(20) NOT NULL,
  `husband_status` varchar(30) NOT NULL,
  `husband_citizenship` text NOT NULL,
  `husband_father` text NOT NULL,
  `husband_mother` text NOT NULL,
  `wife_first_name` varchar(30) NOT NULL,
  `wife_middle_name` varchar(30) NOT NULL,
  `wife_last_name` varchar(30) NOT NULL,
  `wife_age` varchar(20) NOT NULL,
  `wife_status` varchar(30) NOT NULL,
  `wife_citizenship` text NOT NULL,
  `wife_father` text NOT NULL,
  `wife_mother` text NOT NULL,
  `reg_date` text NOT NULL,
  `marriage_date` text NOT NULL,
  `marriage_place` text NOT NULL,
  `remarks` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `birth`
--
ALTER TABLE `birth`
  ADD PRIMARY KEY (`birth_id`);

--
-- Indexes for table `death`
--
ALTER TABLE `death`
  ADD PRIMARY KEY (`death_id`);

--
-- Indexes for table `marriage`
--
ALTER TABLE `marriage`
  ADD PRIMARY KEY (`marriage_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `birth`
--
ALTER TABLE `birth`
  MODIFY `birth_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=296;

--
-- AUTO_INCREMENT for table `death`
--
ALTER TABLE `death`
  MODIFY `death_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `marriage`
--
ALTER TABLE `marriage`
  MODIFY `marriage_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
