package com.trxyzng.trung.post.check_vote_post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckVotePostService {
    @Autowired
    CheckVotePostRepo checkVotePostRepo;

    public String findVoteTypeByUidAndPostId(int uid, int postId) {
        return checkVotePostRepo.findVoteTypeByUidAndPostId(uid, postId).orElse("");
    }

    public VotePostEntity saveCheckVotePostEntity(int uid, int postId, String vote_type) {
        VotePostEntity votePostEntity = new VotePostEntity(uid, postId, vote_type);
        return checkVotePostRepo.save(votePostEntity);
    }

    public void updateVoteTypeByUidAndPostId(int uid, int postId, String newVoteType) {
        checkVotePostRepo.updateVoteTypeByUidAndPostId(uid, postId, newVoteType);
    }
 }

