-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： localhost
-- 產生時間： 2022 年 08 月 12 日 06:31
-- 伺服器版本： 10.4.21-MariaDB
-- PHP 版本： 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `tc20`
--

-- --------------------------------------------------------

--
-- 資料表結構 `tc_uploadfile`
--

CREATE TABLE `tc_uploadfile` (
  `fid` int(11) NOT NULL COMMENT '自增ID',
  `dateline` varchar(200) NOT NULL COMMENT '上傳時間',
  `userip` varchar(200) NOT NULL COMMENT '客戶端IP地址',
  `uploadfile` varchar(200) NOT NULL COMMENT '上傳後文件存放地址',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '文件的狀態'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='上傳文件表';

--
-- 傾印資料表的資料 `tc_uploadfile`
--

INSERT INTO `tc_uploadfile` (`fid`, `dateline`, `userip`, `uploadfile`, `status`) VALUES
(4, '1660119385', '::1', 'uploadfile/1660119385FG.png', 0),
(3, '1660118807', '::1', 'uploadfile/1660118807FG.png', 0),
(5, '1660120742', '10.39.180.149', 'uploadfile/1660120742c87b0f4996eb3b58ba1cc2fef19f19b5.jpeg', 0),
(6, '1660121071', '10.39.180.149', 'uploadfile/1660121071c87b0f4996eb3b58ba1cc2fef19f19b5.jpeg', 0),
(7, '1660193475', '10.39.180.153', 'uploadfile/1660193475thegtgname.jpeg', 0),
(8, '1660206017', '10.39.180.190', 'uploadfile/1660206017thegtgname.jpeg', 0),
(9, '1660206139', '10.39.180.190', 'uploadfile/1660206139thegtgname.jpeg', 0),
(10, '1660278172', '10.39.180.171', 'uploadfile/1660278172thegtgname.jpeg', 0),
(11, '1660278279', '10.39.180.171', 'uploadfile/1660278279thegtgname.jpeg', 0);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `tc_uploadfile`
--
ALTER TABLE `tc_uploadfile`
  ADD PRIMARY KEY (`fid`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tc_uploadfile`
--
ALTER TABLE `tc_uploadfile`
  MODIFY `fid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID', AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
