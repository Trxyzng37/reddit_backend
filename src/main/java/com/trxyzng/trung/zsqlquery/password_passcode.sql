create table SECURITY.password_passcode (
    email varchar(255) not null constraint password_passcode_CT_email check(datalength(email) >=3 and datalength(email) <= 255),
    passcode int not null constraint password_passcode_CT_passcode check(passcode >= 100000 and passcode <= 999999),
    created_at DATETIMEOFFSET not null,
    expiration_at AS DATEADD(MINUTE, 3, created_at),
    constraint password_passcode_PK_email primary key (email),
    constraint password_passcode_FK_email foreign key (email) references USER_DATA.users (email)
)