create table INFO.user_profile (
    uid int not null,
    name varchar(30) not null,
    description varchar(255) not null default 'description for user',
    created_at DATETIMEOFFSET not null,
    karma int not null default 1,
    icon_base64 varchar(max) not null,
    constraint user_info_PK_uid primary key (uid),
    constraint user_info_FK_uid_users foreign key (uid) references USER_DATA.users (uid),
	constraint user_info_CT_name unique(name),
	constraint user_info_CT_icon_base64 check(datalength(icon_base64) >= 0 and datalength(icon_base64) <=100000),
)