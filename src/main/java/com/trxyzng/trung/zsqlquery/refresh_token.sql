create table SECURITY.refresh_token (
    uid int not null constraint refresh_token_CT_uid check(uid >= 100000 and uid <= 999999),
    refresh_token varchar(1000) not null,
    constraint refresh_token_PK_refresh_token primary key (refresh_token),
    constraint refresh_token_FK_uid_users foreign key (uid) references USER_DATA.users (uid)
)
go