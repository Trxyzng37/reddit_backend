create table USER_DATA.users (
	uid int not null default (next value for seq_uid) constraint users_CT_uid check(uid >= 100000 and uid <= 999999),
	username varchar(30) not null constraint users_CT_username check(datalength(username) >= 2 and datalength(username) <= 30) constraint ct_unique_username unique(username),
	password varchar(7000) not null constraint users_CT_password check(datalength(password) >= 1 and datalength(password) <= 7000),
	email varchar(255) not null constraint users_CT_email check(datalength(email) >=2 and datalength(email) <= 100) constraint ct_unique_email unique(email),
	constraint users_PK_uid primary key (uid)
)

insert into USER_DATA.users (uid, username, password, email) values (100088, 'test', 'password', 'phuong@gmail.com')


