/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 5.6.12-log : Database - kiddoapp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kiddoapp` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `kiddoapp`;

/*Table structure for table `child` */

DROP TABLE IF EXISTS `child`;

CREATE TABLE `child` (
  `chid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `cname` varchar(50) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `photo` varchar(250) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `class` varchar(10) DEFAULT NULL,
  `school` varchar(100) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `clid` int(11) DEFAULT NULL,
  PRIMARY KEY (`chid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `child` */

insert  into `child`(`chid`,`pid`,`cname`,`age`,`photo`,`gender`,`class`,`school`,`username`,`clid`) values 
(1,1,'alaka',10,'/static/child/a.jpg','female','5','providence','alaka@g.com',4);

/*Table structure for table `interests` */

DROP TABLE IF EXISTS `interests`;

CREATE TABLE `interests` (
  `int_id` int(11) NOT NULL AUTO_INCREMENT,
  `chid` int(10) DEFAULT NULL,
  `tagtype` varchar(100) DEFAULT NULL,
  `answer` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`int_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Data for the table `interests` */

insert  into `interests`(`int_id`,`chid`,`tagtype`,`answer`) values 
(1,4,'Location','Calicut'),
(2,4,'School name','Providence'),
(7,4,'Subjects','Gajini'),
(8,4,'Science','biology'),
(9,4,'Languages','tamil'),
(10,4,'Ambition','doctor'),
(11,4,'News Topics','Current affairs'),
(12,4,'Genres of books','scifi'),
(13,4,'Drawings','doodles'),
(14,4,'Arts','Music'),
(15,4,'Music','Western music'),
(16,4,'Places you like to visit','Canada'),
(17,4,'Tv Shows','Boys over flowers'),
(18,4,'Hobbies','Reading'),
(19,4,'Fav Song','I will return'),
(20,4,'Dance Videos','Shakira'),
(21,4,'Artists','Madonna'),
(22,4,'Fashion','Gucci'),
(23,4,'Magazines','vanitha');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `LID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `UserType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`LID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`LID`,`Username`,`Password`,`UserType`) values 
(1,'anvi@gmail.com','anvi','parent'),
(2,'ashika@gmail.com','ashi','parent'),
(4,'alaka@g.com','alaka','child');

/*Table structure for table `parent` */

DROP TABLE IF EXISTS `parent`;

CREATE TABLE `parent` (
  `pid` int(11) DEFAULT NULL,
  `pname` varchar(80) DEFAULT NULL,
  `photo` varchar(250) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `phone` bigint(50) DEFAULT NULL,
  `email` varchar(80) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(50) DEFAULT NULL,
  `clid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `parent` */

insert  into `parent`(`pid`,`pname`,`photo`,`gender`,`age`,`phone`,`email`,`place`,`post`,`pin`,`clid`) values 
(1,'Anvi','/static/parent/220426-095519.jpg','Female',27,2147483647,'anvi@gmail.com','Modakkallur','Atholi',673018,1),
(2,'Ashika','/static/parent/220426-095522.jpg','Female',26,9090909090,'ashika@gmail.com','Nadakkaav','Kozhikode',673018,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
