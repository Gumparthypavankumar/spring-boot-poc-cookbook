DROP SCHEMA IF EXISTS `distributed_scheduling`;
CREATE SCHEMA IF NOT EXISTS `distributed_scheduling`;
USE `distributed_scheduling`;

CREATE TABLE IF NOT EXISTS `cd_users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `email_delivery_status` ENUM('PENDING', 'DELIVERED'),
    `created_by` VARCHAR(255) NOT NULL DEFAULT 'System-User',
    `last_modified_by` VARCHAR(255) NOT NULL,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk1_cd_users PRIMARY KEY (`id`),
    CONSTRAINT uk_users_email UNIQUE (`email`)
);
