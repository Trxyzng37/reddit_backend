create table UTILITIES.saved_post (
     uid int not null,
     post_id int not null,
     saved int not null default 0 constraint saved_post_CT_saved check(saved_post.saved in (0,1)),
     created_at DATETIMEOFFSET not null,
     constraint saved_post_FK_post_id foreign key (post_id) references INFO.post (post_id),
     constraint saved_post_FK_uid foreign key (uid) references USER_DATA.users (uid),
     constraint saved_post_PK_uid_post_id primary key (uid, post_id)
)