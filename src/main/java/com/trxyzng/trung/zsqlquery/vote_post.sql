create table INFO.vote_info (
    uid int not null,
    post_id int not null,
    vote_type varchar(8) not null constraint vote_info_CT_vote_type check(vote_type in ('none', 'upvote', 'downvote')),
    constraint vote_info_PK_uid primary key (uid, post_id),
    constraint vote_info_FK_uid foreign key (uid) references USER_DATA.users (uid),
    constraint vote_info_FK_post_id foreign key (post_id) references INFO.post (post_id)
)