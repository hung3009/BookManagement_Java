-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 27, 2024 at 08:47 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbbook`
--

-- --------------------------------------------------------

--
-- Table structure for table `authors`
--

CREATE TABLE `authors` (
  `AuthorID` int(11) NOT NULL,
  `AuthorName` varchar(255) NOT NULL,
  `IsActive` tinyint(1) DEFAULT 1,
  `Email` varchar(255) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `BirthDate` date DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `Hometown` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `authors`
--

INSERT INTO `authors` (`AuthorID`, `AuthorName`, `IsActive`, `Email`, `PhoneNumber`, `BirthDate`, `Gender`, `Hometown`) VALUES
(1, 'Ngô Văn Tuấn', 1, 'tuan@gmail.com', '123456789', '2024-02-04', 'nam', 'Hà Nội'),
(2, 'Hồng Thất Công', 1, 'cong@gmail.com', '123456789', '2024-02-01', 'Nam', 'Hà Bắc');

-- --------------------------------------------------------

--
-- Table structure for table `bookbatches`
--

CREATE TABLE `bookbatches` (
  `LotID` int(11) NOT NULL,
  `BookID` int(11) NOT NULL,
  `QuantityOriginal` int(11) DEFAULT NULL,
  `QuantityCurrent` int(11) DEFAULT NULL,
  `PurchaseDate` date DEFAULT NULL,
  `UnitPrice` decimal(10,2) DEFAULT NULL,
  `ReceiptDetailID` int(11) NOT NULL,
  `StorageLocation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookbatches`
--

INSERT INTO `bookbatches` (`LotID`, `BookID`, `QuantityOriginal`, `QuantityCurrent`, `PurchaseDate`, `UnitPrice`, `ReceiptDetailID`, `StorageLocation`) VALUES
(33, 1, 5, 5, '2024-02-27', 20000.00, 33, 'Tủ 1'),
(34, 3, 10, 10, '2024-02-27', 30000.00, 34, 'Tủ 2'),
(35, 7, 5, 5, '2024-02-27', 100000.00, 35, 'tủ 3'),
(36, 3, 5, 5, '2024-02-27', 40000.00, 36, 'tủ 4'),
(37, 3, 5, 5, '2024-02-27', 50000.00, 37, 'tủ sách 3');

-- --------------------------------------------------------

--
-- Table structure for table `bookcategories`
--

CREATE TABLE `bookcategories` (
  `CategoryID` int(11) NOT NULL,
  `CategoryName` varchar(255) NOT NULL,
  `IsActive` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookcategories`
--

INSERT INTO `bookcategories` (`CategoryID`, `CategoryName`, `IsActive`) VALUES
(1, 'Lịch sử', 1),
(2, 'Chính trị', 1),
(3, 'Trinh thám', 1);

-- --------------------------------------------------------

--
-- Table structure for table `bookreceiptdetails`
--

CREATE TABLE `bookreceiptdetails` (
  `ReceiptDetailID` int(11) NOT NULL,
  `ReceiptID` varchar(20) NOT NULL,
  `BookID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `UnitPrice` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookreceiptdetails`
--

INSERT INTO `bookreceiptdetails` (`ReceiptDetailID`, `ReceiptID`, `BookID`, `Quantity`, `UnitPrice`) VALUES
(33, 'PN20240227060739', 1, 5, 20000.00),
(34, 'PN20240227060739', 3, 10, 30000.00),
(35, 'PN20240227064056', 7, 5, 100000.00),
(36, 'PN20240227064056', 3, 5, 40000.00),
(37, 'PN20240227153514', 3, 5, 50000.00);

-- --------------------------------------------------------

--
-- Table structure for table `bookreceipts`
--

CREATE TABLE `bookreceipts` (
  `ReceiptID` varchar(20) NOT NULL,
  `ReceiptDate` date NOT NULL,
  `EmployeeID` int(11) DEFAULT NULL,
  `TotalCost` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookreceipts`
--

INSERT INTO `bookreceipts` (`ReceiptID`, `ReceiptDate`, `EmployeeID`, `TotalCost`) VALUES
('PN20240227060739', '2024-02-27', 2, 400000.00),
('PN20240227064056', '2024-02-27', 2, 700000.00),
('PN20240227153514', '2024-02-27', 2, 250000.00);

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `BookID` int(11) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `ISBN` varchar(13) NOT NULL,
  `CategoryID` int(11) DEFAULT NULL,
  `PublisherID` int(11) DEFAULT NULL,
  `AuthorID` int(11) DEFAULT NULL,
  `IsActive` tinyint(1) DEFAULT 1,
  `NumberOfPages` int(11) DEFAULT NULL,
  `Size` varchar(255) DEFAULT NULL,
  `publicationDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`BookID`, `Title`, `ISBN`, `CategoryID`, `PublisherID`, `AuthorID`, `IsActive`, `NumberOfPages`, `Size`, `publicationDate`) VALUES
(1, 'Tiếu ngạo giang hồ', 'VN', 2, 2, 1, 1, 2700, '17x30', '2024-02-01'),
(3, 'Việt Nam sử lược', 'VN-01', 1, 1, 2, 1, 900, '17x30', '2022-02-16'),
(4, 'Anh hùng xạ điêu', 'VN-TQ', 3, 1, 2, 1, 2400, '17x30', '2024-02-24'),
(6, 'Lộc đỉnh ký', 'VN-VKL', 1, 1, 1, 1, 3000, '17x30', '2020-02-21'),
(7, 'Đường về nô lệ', 'QT', 2, 2, 2, 1, 300, '17x30', '2020-02-26');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `CustomerID` int(11) NOT NULL,
  `CustomerName` varchar(255) NOT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `ContactNumber` varchar(20) DEFAULT NULL,
  `IsMember` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CustomerID`, `CustomerName`, `Address`, `ContactNumber`, `IsMember`) VALUES
(1, 'Hoàng Dược Sư', 'Đảo đào hoa', '0123456789', 1),
(2, 'Mộ Dung Long Thành', 'Cô Tô Yến Tử Ổ', '09123488383', 0);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

CREATE TABLE `orderdetails` (
  `OrderDetailID` int(11) NOT NULL,
  `OrderID` int(11) DEFAULT NULL,
  `BookID` int(11) DEFAULT NULL,
  `Quantity` int(11) NOT NULL,
  `UnitPrice` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderID` int(11) NOT NULL,
  `OrderDate` date NOT NULL,
  `CustomerID` int(11) DEFAULT NULL,
  `Discount` decimal(5,2) DEFAULT 0.00,
  `IsActive` tinyint(1) DEFAULT 1,
  `EmployeeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `publishers`
--

CREATE TABLE `publishers` (
  `PublisherID` int(11) NOT NULL,
  `PublisherName` varchar(255) NOT NULL,
  `IsActive` tinyint(1) DEFAULT 1,
  `Address` varchar(255) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `publishers`
--

INSERT INTO `publishers` (`PublisherID`, `PublisherName`, `IsActive`, `Address`, `PhoneNumber`, `Email`) VALUES
(1, 'NXB Tri thức', 1, 'Hà Nội', '123456789', 'nxb.trithuc@gmail.com'),
(2, 'NXB Sự thật', 1, 'Sài Gòn', '123456789', 'nxb.suthat@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `RoleId` int(11) NOT NULL,
  `RoleName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`RoleId`, `RoleName`) VALUES
(1, 'Admin'),
(2, 'Employee');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `IsActive` tinyint(1) DEFAULT 1,
  `Fullname` varchar(255) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `RoleId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Username`, `Password`, `IsActive`, `Fullname`, `Email`, `PhoneNumber`, `Address`, `Gender`, `RoleId`) VALUES
(1, 'ADMIN', '1111', 1, 'Tuấn ADMIN', NULL, NULL, NULL, NULL, 1),
(2, 'NV001', '1111', 1, 'Ngô Văn Tuấn', NULL, NULL, NULL, NULL, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`AuthorID`);

--
-- Indexes for table `bookbatches`
--
ALTER TABLE `bookbatches`
  ADD PRIMARY KEY (`LotID`),
  ADD KEY `BookID` (`BookID`),
  ADD KEY `ReceiptDetailID` (`ReceiptDetailID`);

--
-- Indexes for table `bookcategories`
--
ALTER TABLE `bookcategories`
  ADD PRIMARY KEY (`CategoryID`);

--
-- Indexes for table `bookreceiptdetails`
--
ALTER TABLE `bookreceiptdetails`
  ADD PRIMARY KEY (`ReceiptDetailID`),
  ADD KEY `BookID` (`BookID`),
  ADD KEY `fk_book_receipt` (`ReceiptID`);

--
-- Indexes for table `bookreceipts`
--
ALTER TABLE `bookreceipts`
  ADD PRIMARY KEY (`ReceiptID`),
  ADD KEY `EmployeeID` (`EmployeeID`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`BookID`),
  ADD UNIQUE KEY `ISBN` (`ISBN`),
  ADD KEY `CategoryID` (`CategoryID`),
  ADD KEY `PublisherID` (`PublisherID`),
  ADD KEY `AuthorID` (`AuthorID`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Indexes for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`OrderDetailID`),
  ADD KEY `OrderID` (`OrderID`),
  ADD KEY `BookID` (`BookID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`),
  ADD KEY `CustomerID` (`CustomerID`),
  ADD KEY `idx_employee_id` (`EmployeeID`);

--
-- Indexes for table `publishers`
--
ALTER TABLE `publishers`
  ADD PRIMARY KEY (`PublisherID`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`RoleId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD KEY `FK_Users_Roles` (`RoleId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `authors`
--
ALTER TABLE `authors`
  MODIFY `AuthorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `bookbatches`
--
ALTER TABLE `bookbatches`
  MODIFY `LotID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `bookcategories`
--
ALTER TABLE `bookcategories`
  MODIFY `CategoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `bookreceiptdetails`
--
ALTER TABLE `bookreceiptdetails`
  MODIFY `ReceiptDetailID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `BookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `orderdetails`
--
ALTER TABLE `orderdetails`
  MODIFY `OrderDetailID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `publishers`
--
ALTER TABLE `publishers`
  MODIFY `PublisherID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `RoleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookbatches`
--
ALTER TABLE `bookbatches`
  ADD CONSTRAINT `bookbatches_ibfk_1` FOREIGN KEY (`BookID`) REFERENCES `books` (`BookID`),
  ADD CONSTRAINT `bookbatches_ibfk_2` FOREIGN KEY (`ReceiptDetailID`) REFERENCES `bookreceiptdetails` (`ReceiptDetailID`);

--
-- Constraints for table `bookreceiptdetails`
--
ALTER TABLE `bookreceiptdetails`
  ADD CONSTRAINT `bookreceiptdetails_ibfk_2` FOREIGN KEY (`BookID`) REFERENCES `books` (`BookID`),
  ADD CONSTRAINT `fk_book_receipt` FOREIGN KEY (`ReceiptID`) REFERENCES `bookreceipts` (`ReceiptID`);

--
-- Constraints for table `bookreceipts`
--
ALTER TABLE `bookreceipts`
  ADD CONSTRAINT `bookreceipts_ibfk_1` FOREIGN KEY (`EmployeeID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `bookcategories` (`CategoryID`),
  ADD CONSTRAINT `books_ibfk_2` FOREIGN KEY (`PublisherID`) REFERENCES `publishers` (`PublisherID`),
  ADD CONSTRAINT `books_ibfk_3` FOREIGN KEY (`AuthorID`) REFERENCES `authors` (`AuthorID`);

--
-- Constraints for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`),
  ADD CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`BookID`) REFERENCES `books` (`BookID`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK_Orders_Employee` FOREIGN KEY (`EmployeeID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customers` (`CustomerID`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_Users_Roles` FOREIGN KEY (`RoleId`) REFERENCES `roles` (`RoleId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
