DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `up_file`;
CREATE TABLE `up_file` (
  `username` varchar(20) NOT NULL,
  `filename` varchar(128) NOT NULL,
  `zhouzhong` tinyint(4) DEFAULT NULL,
  `zhidong` tinyint(4) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `up_time` datetime NOT NULL,
  PRIMARY KEY (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






