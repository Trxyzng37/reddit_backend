package com.trxyzng.trung.post.check_vote_post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CheckVotePostRepo extends JpaRepository<VotePostEntity, Integer> {

    @Query("select t.vote_type from VotePostEntity t where t.uid = :uid and t.post_id = :postId")
    Optional<String> findVoteTypeByUidAndPostId(@Param("uid") int uid, @Param("postId") int postId);

    @Modifying
    @Query("update VotePostEntity t set t.vote_type = :newVoteType where t.uid = :uid and t.post_id = :postId")
    void updateVoteTypeByUidAndPostId(@Param("uid") int uid, @Param("postId") int postId, @Param("newVoteType") String newVoteType);

    VotePostEntity save(VotePostEntity votePostEntity);
}
