CREATE TABLE `routine` (
                           `routine_id` int NOT NULL AUTO_INCREMENT,
                           `user_id` int NOT NULL,
                           `menu_id` int NOT NULL,
                           `menu_detail_id` int NOT NULL,
                           `routine_day` int NOT NULL,
                           `routine_time` time NOT NULL,
                           `is_activated` tinyint(1) NOT NULL DEFAULT '1',
                           PRIMARY KEY (`routine_id`),
                           KEY `routine_ibfk_3` (`menu_detail_id`),
                           KEY `menu_id` (`menu_id`),
                           KEY `user_id` (`user_id`),
                           CONSTRAINT `routine_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
                           CONSTRAINT `routine_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
                           CONSTRAINT `routine_ibfk_3` FOREIGN KEY (`menu_detail_id`) REFERENCES `menudetails` (`menu_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

