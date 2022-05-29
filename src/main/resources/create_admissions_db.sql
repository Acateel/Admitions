/*******************************************************************************
   Admissions
   Script: create_admissions_db.sql
   DB Server: MySql
   Author: Taras Kozodoi
********************************************************************************/

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
DROP DATABASE IF EXISTS `admissions`;


/*******************************************************************************
   Create database
********************************************************************************/
CREATE DATABASE `admissions`;


USE `admissions`;


/*******************************************************************************
   Create Tables
********************************************************************************/

CREATE TABLE `Faculties`
(
	id serial not null,
    faculty_name text not null,
    budget_seats int not null,
    total_seats int not null
);

CREATE TABLE `User`
(
	id serial not null,
    login varchar(255) not null,
    `password` varchar(255) not null,
    `role` enum('admin', 'applicant') not null,
    applicant_id int
);

CREATE TABLE `Applicant`
(
	id serial not null,
    last_name varchar(255) not null,
    `name` varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255) not null,
    city varchar(255) not null,
    region varchar(255) not null,
    name_educational_institution text not null,
    attestation blob,
    `block` bool not null
);

CREATE TABLE `Request`
(
	id serial not null,
    `status` enum('not processed', 'budget', 'recommend budget', 'contract', 'recommend contract', 'allowed', 'not allowed') not null,
    faculties_id int not null,
    applicant_id int not null,
    main_subject int not null,
    second_subject int not null,
    sub_subject int not null,
    rating_score int not null,
    average_attestation_score float not null,
    publish_time time not null
);

/*******************************************************************************
   Insert data
********************************************************************************/

INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES ('Факультет кібернетики', '1', '2');
INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES ('Юридичний факультет', '1', '2');
INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES ('Факультет прикладної математики', '1', '1');
INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES ('Факультет філології', '1', '2');
INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES ('Факультет авіаційної інженерії', '1', '1');

INSERT INTO `user` (login, password, role, applicant_id) 
VALUES ('admin', 'admin', 'admin', 0);

INSERT INTO applicant (last_name, `name`, surname, email, city, region, name_educational_institution, attestation, `block`) 
VALUES ('Козодой', 'Тарас', 'Романович', 'akateely@gmail.com', 'Володимирець', 'Рівне', 'Школа #1', null, false);

INSERT INTO `user` (login, password, role, applicant_id) 
VALUES ('akateely@gmail.com', '12345678', 'applicant', 1);

INSERT INTO applicant (last_name, `name`, surname, email, city, region, name_educational_institution, attestation, `block`) 
VALUES ('Osmolovich', 'Andriy', 'Victorovich', 'osmolovich@gmail.com', 'Kiyv', 'Kiyv', 'College #22', null, false);

INSERT INTO `user` (login, password, role, applicant_id) 
VALUES ('osmolovich@gmail.com', '12345678', 'applicant', 2);

INSERT INTO applicant (last_name, `name`, surname, email, city, region, name_educational_institution, attestation, `block`) 
VALUES ('Добрін', 'Сергій', 'Вікторович', 'dobrin@gmail.com', 'Київ', 'Київський', 'Колегіум №2', null, false);

INSERT INTO `user` (login, password, role, applicant_id) 
VALUES ('dobrin@gmail.com', '12345678', 'applicant', 3);

INSERT INTO applicant (last_name, `name`, surname, email, city, region, name_educational_institution, attestation, `block`) 
VALUES ('Любомир', 'Валентина', 'Вікторівна', 'lubomur@gmail.com', 'Київ', 'Київський', 'Колегіум №2', null, false);

INSERT INTO `user` (login, password, role, applicant_id) 
VALUES ('lubomur@gmail.com', '12345678', 'applicant', 4);

INSERT INTO applicant (last_name, `name`, surname, email, city, region, name_educational_institution, attestation, `block`) 
VALUES ('Волошин', 'Кіріл', 'Максимович', 'voloshin@gmail.com', 'Київ', 'Київський', 'Школа №23', null, false);

INSERT INTO `user` (login, password, role, applicant_id) 
VALUES ('voloshin@gmail.com', '12345678', 'applicant', 5);

INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 1, 1, 195, 196, 179, 0, 10.8, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 3, 1, 195, 196, 179, 0, 10.8, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 5, 1, 195, 196, 179, 0, 10.8, now());

INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 1, 2, 188, 196, 179, 0, 9.6, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 3, 2, 196, 188, 196, 0, 9.6, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 4, 2, 172, 196, 179, 0, 9.6, now());

INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 1, 3, 150, 149, 135, 0, 7.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 2, 3, 150, 149, 135, 0, 7.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 3, 3, 150, 149, 135, 0, 7.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 4, 3, 150, 149, 135, 0, 7.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 5, 3, 150, 149, 135, 0, 7.2, now());

INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 2, 4, 188, 172, 190, 0, 11.6, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 4, 4, 188, 172, 190, 0, 11.6, now());

INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 1, 5, 196, 149, 192, 0, 11.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 2,5, 192, 196, 188, 0, 11.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 3, 5, 196, 188, 192, 0, 11.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 4, 5, 189, 196, 188, 0, 11.2, now());
INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time)
VALUES ('not processed', 5, 5, 196, 196, 188, 0, 11.2, now());

