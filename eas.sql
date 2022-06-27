-- educational administration system EAS
drop database if exists EAS;

create database EAS 
character set utf8mb4
collate utf8mb4_unicode_ci; 

use EAS;

create table administrator(
    `id` int unsigned not null primary key auto_increment,
    `username` varchar(256) not null,
    `password` varchar(256) not null
);

insert into administrator(`username`,`password`) values('admin','admin');

create table student(
    `id` int unsigned not null primary key auto_increment,
    `name` varchar(256) not null,
    `student_number` varchar(256) not null unique,
    `password` varchar(256) not null
);

insert into student(`name`,`student_number`,`password`) values('张三','2022001','001');
insert into student(`name`,`student_number`,`password`) values('李四','2022002','002');
insert into student(`name`,`student_number`,`password`) values('王五','2022003','003');
insert into student(`name`,`student_number`,`password`) values('赵六','2022004','004');

create table grade(
    `id` int unsigned not null primary key auto_increment,
    `exam_number` int unsigned not null,
    `student_id` int not null,
    `subject_one` varchar(256) default '0', 
    `subject_two` varchar(256) default '0', 
    `total` varchar(256) default '0',
    `rank` int 
);

insert into grade(`exam_number`,`student_id`,`subject_one`,`subject_two`,`total`,`rank`) 
values(1,1,'100','100','200',1);
insert into grade(`exam_number`,`student_id`,`subject_one`,`subject_two`,`total`,`rank`) 
values(1,2,'100','99','199',2);
insert into grade(`exam_number`,`student_id`,`subject_one`,`subject_two`,`total`,`rank`) 
values(1,3,'100','98','198',3);
insert into grade(`exam_number`,`student_id`,`subject_one`,`subject_two`,`total`,`rank`) 
values(1,4,'99','99','198',3);