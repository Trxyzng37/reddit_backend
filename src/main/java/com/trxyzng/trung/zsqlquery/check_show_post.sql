create table UTILITIES.show_post (
      uid int not null,
      post_id int not null,
      show int not null default 0 constraint show_post_CT_show check(show in (0,1)),
      constraint show_post_FK_post_id foreign key (post_id) references INFO.post (post_id),
      constraint show_post_FK_uid foreign key (uid) references USER_DATA.users (uid),
      constraint show_post_PK_uid_post_id primary key (uid, post_id)
)