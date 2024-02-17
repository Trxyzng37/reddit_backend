create table USER_DATA.oath2_user (
    uid int not null default (next value for seq_uid) constraint oath2_user_CT_uid check(uid >= 100000 and uid <= 999999),
    email varchar(255) not null constraint oath2_user_CT_email check(datalength(email) >=3 and datalength(email) <= 100) constraint oath2_user_CT_unique_email unique(email),
    constraint oath2_user_PK_uid primary key (uid)
)