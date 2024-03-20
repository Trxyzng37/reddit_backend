create table INFO.community (
	community_id int not null default (next value for seq_community_id),
    name varchar(30) not null,
    description varchar(255) not null default 'description for community',
	created_at DATETIMEOFFSET not null,
	subscriber_count int not null default 1,
	icon_base64 varchar(max) not null,
	constraint community_CT_name unique(name),
	constraint community_CT_community_id unique(community_id),
	constraint community_CT_icon_base64 check(datalength(icon_base64) >= 0 and datalength(icon_base64) <=100000),
    constraint community_PK_community_id primary key (community_id)
)