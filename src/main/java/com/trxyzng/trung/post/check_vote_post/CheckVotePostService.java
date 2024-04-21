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

    public Integer saveCheckVotePostEntity(int uid, int postId, String vote_type) {
        return checkVotePostRepo.saveCheckVotePostEntity(uid, postId, vote_type);
    }

    public void updateVoteTypeByUidAndPostId(int uid, int postId, String newVoteType) {
        checkVotePostRepo.updateVoteTypeByUidAndPostId(uid, postId, newVoteType);
    }
 }

