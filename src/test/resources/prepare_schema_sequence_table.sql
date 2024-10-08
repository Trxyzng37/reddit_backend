DROP SEQUENCE IF EXISTS seq_uid CASCADE;
DROP SEQUENCE IF EXISTS seq_comment_id CASCADE;
DROP SEQUENCE IF EXISTS seq_post_id CASCADE;
DROP SEQUENCE IF EXISTS seq_community_id CASCADE;

DROP SCHEMA IF EXISTS USER_DATA CASCADE;
DROP SCHEMA IF EXISTS INFO CASCADE;
DROP SCHEMA IF EXISTS SECURITY CASCADE;
DROP SCHEMA IF EXISTS TEMPORARY CASCADE;
DROP SCHEMA IF EXISTS UTILITIES CASCADE;

--create schema, sequence and tables use for test without data
--schema
create schema USER_DATA;
create schema INFO;
create schema SECURITY;
create schema TEMPORARY;
create schema UTILITIES;

--sequence
create sequence seq_uid
as int
minvalue 100000
maxvalue 999999
start 900000;

create sequence seq_comment_id
as int
minvalue 100000
maxvalue 999999
start 900000;

create sequence seq_post_id
as int
minvalue 100000
maxvalue 999999
start 900000;

create sequence seq_community_id
as int
minvalue 100000
maxvalue 999999
start 900000;

--table
create table USER_DATA.users (
	uid int not null default nextval('seq_uid') check(uid >= 100000 and uid <= 999999),
	username varchar(16) not null unique check(length(username) >= 2 and length(username) <= 16),
	password varchar(7000) not null check(length(password) >= 1 and length(password) <= 7000),
	email varchar(255) not null unique check(length(email) >=3 and length(email) <= 255),
	constraint users_PK_uid primary key (uid)
);

create table INFO.community (
	id int not null default nextval('seq_community_id'),
    name varchar(30) not null,
    uid int not null,
    description varchar(300) not null default 'description for community',
	created_at TIMESTAMP not null,
	subscriber_count int not null default 0,
	avatar varchar not null,
	banner varchar,
	scope int not null default 0 check(scope in (0,1)),
	deleted int not null default 0 check(deleted in (0,1)),
	constraint community_CT_name unique(name),
	constraint community_CT_id unique(id),
    constraint community_PK_id primary key (id),
    constraint community_FK_uid foreign key (uid) references USER_DATA.users (uid)
);

create table INFO.post (
    post_id int not null default nextval('seq_post_id'),
    type varchar(10) not null check(type in ('editor', 'img', 'link', 'video')),
    uid int not null,
    community_id int not null,
    title varchar(300) not null,
    content varchar,
    created_at TIMESTAMP not null,
    vote int not null default 0,
    deleted int not null default 0 check(deleted in (0, 1)),
	allow int not null default 0 check(allow in (0,1)),
	deleted_by int not null default 0 check(deleted_by in (0,1,2)),
	deleted_at TIMESTAMP default null,
	allowed_at TIMESTAMP default null,
	editted int not null default 0 check(editted in (0,1)),
	editted_at TIMESTAMP default null,
    constraint post_PK_id primary key (post_id),
    constraint post_FK_community_id foreign key (community_id) references INFO.community (id),
    constraint post_FK_uid foreign key (uid) references USER_DATA.users (uid)
);

create table SECURITY.change_password_passcode (
    email varchar(255) not null check(length(email) >=3 and length(email) <= 255),
    passcode int not null check(passcode >= 100000 and passcode <= 999999),
    created_at TIMESTAMP not null,
    expiration_at TIMESTAMP not null,
    constraint change_password_passcode_PK_email primary key (email),
    constraint change_password_passcode_FK_email foreign key (email) references USER_DATA.users (email)
);

create table UTILITIES.comment_id (
    id int not null default nextval('seq_comment_id')
);

create table SECURITY.confirm_email_passcode (
    email varchar(255) not null check(length(email) >=3 and length(email) <= 255),
    passcode int not null check(passcode >= 100000 and passcode <= 999999),
    created_at TIMESTAMP not null,
    expiration_at TIMESTAMP not null,
    constraint confirm_email_passcode_PK_email primary key (email)
);

create table UTILITIES.join_community
(
    uid int not null,
    community_id int not null,
    subscribed int not null check(subscribed in (0, 1)),
    constraint subscribed_PK_uid_community_id primary key (uid, community_id),
    constraint subscribed_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint subscribed_FK_community_id foreign key (community_id) references INFO.community (id)
);

create table UTILITIES.recent_visitted_community (
    uid int not null,
    community_id int not null,
    visitted_time TIMESTAMP not null,
    constraint recent_visitted_community_PK_id primary key (uid, community_id),
    constraint recent_visitted_community_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint recent_visitted_community_FK_community_id foreign key (community_id) references INFO.community (id)
);

create table UTILITIES.recent_visitted_post (
    uid int not null,
    post_id int not null,
    visitted_time TIMESTAMP not null,
    constraint recent_visitted_post_PK_id primary key (uid, post_id),
    constraint recent_visitted_post_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint recent_visitted_post_FK_post_id foreign key (post_id) references INFO.post (post_id)
);

create table SECURITY.refresh_token (
    uid int not null check(uid >= 100000 and uid <= 999999),
    refresh_token varchar(2000) not null,
    constraint refresh_token_PK_refresh_token primary key (refresh_token),
    constraint refresh_token_FK_uid_users foreign key (uid) references USER_DATA.users (uid)
);

create table UTILITIES.saved_post (
     uid int not null,
     post_id int not null,
     saved int not null default 0 check(saved_post.saved in (0,1)),
     created_at TIMESTAMP not null,
     constraint saved_post_FK_post_id foreign key (post_id) references INFO.post (post_id),
     constraint saved_post_FK_uid foreign key (uid) references USER_DATA.users (uid),
     constraint saved_post_PK_uid_post_id primary key (uid, post_id)
);

create table TEMPORARY.sign_up_data (
    username varchar(30) not null check(length(username) >= 2 and length(username) <= 30) unique,
    password varchar(7000) not null check(length(password) >= 1 and length(password) <= 7000),
    email varchar(255) not null check(length(email) >=3 and length(email) <= 255) unique,
    constraint sign_up_data_PK_email primary key (email)
);

create table INFO.vote_info (
    uid int not null,
    post_id int not null,
    vote_type varchar(8) not null check(vote_type in ('none', 'upvote', 'downvote')),
    constraint vote_info_PK_uid primary key (uid, post_id),
    constraint vote_info_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint vote_info_FK_post_id foreign key (post_id) references INFO.post (post_id)
);

create table INFO.user_profile (
    uid int not null,
    username VARCHAR(16) not null unique,
    description VARCHAR(255) not null default '',
    created_at TIMESTAMP not null,
    post_karma int not null default 0,
    comment_karma int not null default 0,
    avatar varchar not null default 'https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png',
    constraint user_info_PK_uid primary key (uid),
    constraint user_info_FK_uid_users foreign key (uid) references USER_DATA.users (uid),
	constraint user_info_CT_icon_avatar check(length(avatar) >= 0 and length(avatar) <= 100000)
);

create table UTILITIES.show_post (
      uid int not null,
      post_id int not null,
      show int not null default 0 check(show in (0,1)),
      constraint show_post_FK_post_id foreign key (post_id) references INFO.post (post_id),
      constraint show_post_FK_uid foreign key (uid) references USER_DATA.users (uid),
      constraint show_post_PK_uid_post_id primary key (uid, post_id)
);

