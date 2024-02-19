create table SECURITY.confirm_email_passcode (
    email varchar(255) not null constraint confirm_email_passcode_CT_email check(datalength(email) >=3 and datalength(email) <= 255),
    passcode int not null constraint confirm_email_passcode_CT_passcode check(passcode >= 100000 and passcode <= 999999),
    created_at DATETIMEOFFSET not null,
    expiration_at AS DATEADD(MINUTE, 3, created_at),
    constraint confirm_email_passcode_PK_email primary key (email),
)