create table TEMPORARY.sign_up_data (
    username varchar(30) not null constraint sign_up_data_CT_username check(datalength(username) >= 2 and datalength(username) <= 30) constraint sign_up_data_CT_unique_username unique(username),
    password varchar(7000) not null constraint sign_up_data_CT_password check(datalength(password) >= 1 and datalength(password) <= 7000),
    email varchar(255) not null constraint sign_up_data_CT_email check(datalength(email) >=3 and datalength(email) <= 100) constraint sign_up_data_CT_unique_email unique(email),
    constraint sign_up_data_PK_email primary key (email)
)

