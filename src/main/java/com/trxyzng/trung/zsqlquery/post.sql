create table INFO.post (
    post_id int not null default (next value for seq_post_id),
    type varchar(10) not null constraint post_CT_type check(type in ('editor', 'img', 'link')),
    username varchar(30) not null,
    community_name varchar(30) not null,
    title varchar(300) not null,
    content varchar(max),
    created_at DATETIMEOFFSET not null,
    vote int not null default 1,
    constraint post_PK_id primary key (post_id),
    constraint post_FK_community_id foreign key (community_name) references INFO.community (name),
    constraint post_FK_uid foreign key (username) references USER_DATA.users (username)
)