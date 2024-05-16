create table UTILITIES.recent_visitted_community (
    uid int not null,
    community_id int not null,
    visitted_time DATETIMEOFFSET not null,
    constraint recent_visitted_community_PK_id primary key (uid, community_id),
    constraint recent_visitted_community_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint recent_visitted_community_FK_community_id foreign key (community_id) references INFO.community (id)
)