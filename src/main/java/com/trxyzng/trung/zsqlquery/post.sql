create table INFO.post (
    post_id int not null default (next value for seq_post_id),
    community_id int not null,
    uid int not null,
    created_at DATETIMEOFFSET not null,
    vote int not null default 1,
    constraint post_PK_id primary key (post_id),
    constraint post_FK_community_id foreign key (community_id) references INFO.community (community_id),
    constraint post_FK_uid foreign key (uid) references USER_DATA.users (uid)
)