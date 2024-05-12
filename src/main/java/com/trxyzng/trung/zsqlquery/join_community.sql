create table UTILITIES.join_community
(
    uid int not null,
    community_id int not null,
    subscribed int not null constraint subscribed_CT_subscribed check(subscribed in (0, 1)),
    constraint subscribed_PK_uid_community_id primary key (uid, community_id),
    constraint subscribed_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint subscribed_FK_community_id foreign key (community_id) references INFO.community (id)
)