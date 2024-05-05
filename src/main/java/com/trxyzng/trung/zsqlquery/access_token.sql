create table SECURITY.access_token (
    uid int not null constraint access_token_CT_uid check(uid >= 100000 and uid <= 999999),
    access_token varchar(200) not null,
    constraint access_token_PK_refresh_token primary key (access_token),
    constraint access_token_FK_uid foreign key (uid) references USER_DATA.users (uid)
)
go