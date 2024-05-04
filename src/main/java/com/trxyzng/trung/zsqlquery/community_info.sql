create table INFO.community (
	id int not null default (next value for seq_community_id),
    name varchar(30) not null,
    uid int not null,
    description varchar(255) not null default 'description for community',
	created_at DATETIMEOFFSET not null,
	subscriber_count int not null default 1,
	avatar varchar(max) not null,
	constraint community_CT_name unique(name),
	constraint community_CT_id unique(id),
    constraint community_PK_id primary key (id),
    constraint community_FK_uid foreign key (uid) references USER_DATA.users (uid),
)