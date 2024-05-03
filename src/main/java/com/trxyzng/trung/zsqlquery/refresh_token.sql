create table SECURITY.refresh_token (
    uid int not null constraint refresh_token_CT_uid check(uid >= 100000 and uid <= 999999),
    refresh_token varchar(255) not null,
    constraint refresh_token_PK_refresh_token primary key (refresh_token)
--     constraint refresh_token_CT_check_uid check(SECURITY.is_id_in_tables(uid) = 1)
--     constraint refresh_token_FK_uid_users foreign key (uid) references USER_DATA.users (uid)  ,
)
go