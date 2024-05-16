create table UTILITIES.recent_visitted_post (
    uid int not null,
    post_id int not null,
    visitted_time DATETIMEOFFSET not null,
    constraint recent_visitted_post_PK_id primary key (uid, post_id),
    constraint recent_visitted_post_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint recent_visitted_post_FK_post_id foreign key (post_id) references INFO.post (post_id)
)