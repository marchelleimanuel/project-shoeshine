-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2023 at 05:55 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shoeshine`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserID` char(5) NOT NULL,
  `ProductID` char(5) NOT NULL,
  `Quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`UserID`, `ProductID`, `Quantity`) VALUES
('US001', 'PR003', 3),
('US001', 'PR006', 1),
('US002', 'PR006', 4),
('US002', 'PR009', 1),
('US003', 'PR002', 1),
('US003', 'PR005', 2),
('US003', 'PR008', 6),
('US004', 'PR003', 2),
('US004', 'PR007', 5),
('US008', 'PR003', 1);

-- --------------------------------------------------------

--
-- Table structure for table `mscourier`


-- --------------------------------------------------------

--
-- Table structure for table `msproduct`
--

CREATE TABLE `msproduct` (
  `ProductID` char(5) NOT NULL,
  `ProductName` varchar(30) NOT NULL,
  `ProductPrice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mscup`
--

INSERT INTO `msproduct` (`ProductID`, `ProductName`, `ProductPrice`) VALUES
('PR001', 'Signature Cleaning Kit', 150000),
('PR002', 'Clean & Protect Bundle', 200000),
('PR003', 'Essential Cleaning Kit', 35000),
('PR004', 'Sneaker Laundry Detergent', 30000),
('PR005', 'Original Shoe Cleaning Kit', 50000),
('PR006', 'Signature Bundle',350000),
('PR007', 'Brass Brush', 20000),
('PR008', 'Sneaker Laundry bag', 25000),
('PR009', 'Premium Shoe Cleaning Kit', 100000);

-- --------------------------------------------------------

--
-- Table structure for table `msuser`
--

CREATE TABLE `msuser` (
  `UserID` char(5) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `UserPhoneNumber` varchar(20) NOT NULL,
  `UserEmail` varchar(50) NOT NULL,
  `UserPassword` varchar(20) NOT NULL,
  `UserGender` varchar(10) NOT NULL,
  `UserRole` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `msuser`
--

INSERT INTO `msuser` (`UserID`, `Username`, `UserPhoneNumber`, `UserEmail`, `UserPassword`, `UserGender`, `UserRole`) VALUES
('US001', 'admin','+6281123456789','admin123@gmail.com', 'admin123', 'Male', 'Admin'),
('US002', 'efren','+6281234567890', 'efren@gmail.com', 'efrnnthnl12', 'Male', 'User'),
('US003', 'vncnt','+6281345678901', 'vincent@gmail.com', 'njnjnjnj33', 'Male', 'User'),
('US004', 'obrt','+6281456789012', 'obort@gmail.com', 'makanIk4n', 'Male', 'User'),
('US005', 'feryf','+6281567890123', 'nandi@gmail.com', 'frryndi22', 'Male', 'User'),
('US006', 'tester','+6281678901234', 'tester@gmail.com', 'tester123', 'Female', 'User'),
('US007', 'admintester','+6281789012345', 'admin@gmail.com', 'admin1234', 'Male', 'User'),
('US008', 'cladmin','+6281890123456', 'cl@gmail.com', 'cl123456', 'Male', 'User'),
('US009', 'jingyuansayang', '+6281901234567','starrail@gmail.com', 'jingyuan123', 'Male', 'User'),
('US010', 'stevebauadmin','+6282123456789', 'steve@gmail.com', 'steve123', 'Male', 'User'),
('US011', 'dummyuser','+6212312312312' ,'dummyuser@gmail.com', 'dummyuser123', 'Male', 'User'),
('US012', 'admin', '+62987615289523' ,'admin@gmail.com', 'admin123', 'Male', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` char(5) NOT NULL,
  `ProductID` char(5) NOT NULL,
  `Quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `ProductID`, `Quantity`) VALUES
('TR001', 'PR001', 3),
('TR001', 'PR002', 2),
('TR002', 'PR008', 2),
('TR003', 'PR005', 1),
('TR004', 'PR007', 10),
('TR004', 'PR008', 2),
('TR005', 'PR002', 32),
('TR006', 'PR001', 5),
('TR007', 'PR008', 4),
('TR008', 'PR009', 2),
('TR009', 'PR008', 7),
('TR010', 'PR009', 2),
('TR012', 'PR008', 1),
('TR013', 'PR006', 1),
('TR014', 'PR003', 1),
('TR014', 'PR008', 1),
('TR015', 'PR006', 1),
('TR015', 'PR009', 4),
('TR016', 'PR009', 1),
('TR017', 'PR002', 1),
('TR017', 'PR009', 2),
('TR018', 'PR002', 6),
('TR018', 'PR006', 2),
('TR018', 'PR008', 4);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) NOT NULL,
  `TransactionDate` date NOT NULL,
  `PaymentMethod` char(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `UserID`, `TransactionDate`, `PaymentMethod`) VALUES
('TR001', 'US001', '2023-01-19', 'Debit'),
('TR002', 'US001', '2023-01-20', 'Credit'),
('TR003', 'US001', '2023-02-19', 'Debit'),
('TR004', 'US002', '2022-01-19', 'Debit'),
('TR005', 'US003', '2021-01-19', 'Credit'),
('TR006', 'US004', '2022-05-19', 'Debit'),
('TR007', 'US004', '2022-03-02', 'Credit'),
('TR008', 'US004', '2023-04-25', 'E-Banking'),
('TR009', 'US005', '2023-04-10', 'E-Banking'),
('TR010', 'US005', '2022-04-29', 'E-Banking'),
('TR011', 'US001', '2023-05-19', 'E-Banking'),
('TR012', 'US001', '2023-05-19', 'Debit'),
('TR013', 'US001', '2023-05-19', 'E-Banking'),
('TR014', 'US001', '2023-05-19', 'Debit'),
('TR015', 'US001', '2023-05-20', 'E-Banking'),
('TR016', 'US009', '2023-05-20', 'E-Banking'),
('TR017', 'US010', '2023-05-20', 'E-Banking'),
('TR018', 'US003', '2023-05-22', 'E-Banking');

--
-- Table structure for table `shoetype`
--

CREATE TABLE `shoetype` (
  `ShoeTypeID` char(5) NOT NULL,
  `ShoeTypeName` varchar(30) NOT NULL,
  `ShoeTypePrice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `shoetype`
--

INSERT INTO `shoetype` (`ShoeTypeID`, `ShoeTypeName`, `ShoeTypePrice`) VALUES
('ST001', 'Running Shoes', 10000),
('ST002', 'Casual Shoes', 15000),
('ST003', 'Formal Shoes', 20000),
('ST004', 'Sneakers', 25000),
('ST005', 'Boots', 30000);

--
-- Table structure for table `cleaningtype`
--

CREATE TABLE `cleaningtype` (
  `CleaningTypeID` char(5) NOT NULL,
  `CleaningTypeName` varchar(30) NOT NULL,
  `CleaningTypePrice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cleaningtype`
--

INSERT INTO `cleaningtype` (`CleaningTypeID`, `CleaningTypeName`, `CleaningTypePrice`) VALUES
('CT001', 'Standard Cleaning', 50000),
('CT002', 'Deep Cleaning', 75000),
('CT003', 'Premium Cleaning', 100000),
('CT004', 'Express Cleaning', 65000),
('CT005', 'Custom Cleaning', 120000);




CREATE TABLE `onlinecleaningservice` (
  `OnlineCleaningServiceID` char(5) NOT NULL,
  `UserID` char(5) NOT NULL,
  `ShoeTypeID` char(5) NOT NULL ,
  `CleaningTypeID` char(5) NOT NULL,
  `PaymentMethod` char(15) NOT NULL,
  `UserNotes` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `onlinecleaningservice` (`OnlineCleaningServiceID`, `UserID`, `ShoeTypeID`, `CleaningTypeID`,`PaymentMethod`, `UserNotes` ) VALUES
('OS001', 'US001', 'ST001', 'CT001', 'Credit', 'Please clean my running shoes and apply the standard cleaning process.'),
('OS002', 'US002', 'ST002', 'CT002', 'Debit',  'Deep clean and protect my casual shoes.'),
('OS003', 'US003', 'ST001', 'CT001', 'Credit', 'Standard cleaning for my running shoes.'),
('OS004', 'US002', 'ST003', 'CT003', 'Debit',  'Please perform a premium cleaning on my formal shoes.'),
('OS005', 'US001', 'ST002', 'CT002', 'E-Banking', 'Deep clean and protect my casual shoes. Express service required.');


ALTER TABLE `shoetype` 
  ADD PRIMARY KEY (`ShoeTypeID`);

ALTER TABLE `cleaningtype` 
  ADD PRIMARY KEY (`CleaningTypeID`);

ALTER TABLE `onlinecleaningservice`
  ADD PRIMARY KEY (`OnlineCleaningServiceID`);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`UserID`,`ProductID`),
  ADD KEY `ProductID` (`ProductID`) USING BTREE,
  ADD KEY `UserID` (`UserID`) USING BTREE;

--
-- Indexes for table `mscourier`
--

--
-- Indexes for table `msproduct`
--
ALTER TABLE `msproduct`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `msuser`
--
ALTER TABLE `msuser`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`TransactionID`,`ProductID`),
  ADD KEY `transactiondetail_ibfk_3` (`ProductID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `transactionheader_ibfk_1` (`UserID`);

--
-- Constraints for dumped tables
--

ALTER TABLE `onlinecleaningservice`
  ADD CONSTRAINT `fk_UserID` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ShoeTypeID` FOREIGN KEY (`ShoeTypeID`) REFERENCES `shoetype` (`ShoeTypeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_CleaningTypeID` FOREIGN KEY (`CleaningTypeID`) REFERENCES `cleaningtype` (`CleaningTypeID`) ON DELETE CASCADE ON UPDATE CASCADE;


--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `msproduct` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `FK_detailheader` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transactiondetail_ibfk_3` FOREIGN KEY (`ProductID`) REFERENCES `msproduct` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
