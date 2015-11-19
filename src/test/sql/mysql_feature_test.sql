use test;

create table table_name_case_test (
id int
);

alter table table_name_case_test add column naem varchar(16);

create table data_feature_test (
id  int primary key auto_increment,
name varchar(16) not null,
value varchar(64) not null
);

insert data_feature_test ( `name`, `value`) values ('letter case', 'lower');
insert data_feature_test ( `name`, `value`) values ('upper lwer', 'LOWER');


select * from data_feature_test where `value` = 'lower';

alter table data_feature_test drop column `value1`;

alter table data_feature_test add column `value1` varchar(64) collate utf8_general_bin;


update data_feature_test set value1 = 