create table INFO.user_profile (
    uid int not null,
    username varchar(30) not null,
    description varchar(255) not null default '',
    created_at DATETIMEOFFSET not null,
    post_karma int not null default 0,
    comment_karma int not null default 0,
    avatar varchar(max) not null,
    constraint user_info_PK_uid primary key (uid),
    constraint user_info_FK_uid_users foreign key (uid) references USER_DATA.users (uid),
	constraint user_info_CT_icon_avatar check(datalength(avatar) >= 0 and datalength(avatar) <=100000),
)