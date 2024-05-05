create table INFO.post (
    post_id int not null default (next value for seq_post_id),
    type varchar(10) not null constraint post_CT_type check(type in ('editor', 'img', 'link')),
    uid int not null,
    community_id int not null,
    title varchar(300) not null,
    content varchar(max),
    created_at DATETIMEOFFSET not null,
    vote int not null default 0,
    deleted int not null default 0 constraint post_CT_deleted check(deleted in (0, 1)),
    constraint post_PK_id primary key (post_id),
    constraint post_FK_community_id foreign key (community_id) references INFO.community (id),
    constraint post_FK_uid foreign key (uid) references USER_DATA.users (uid),
)