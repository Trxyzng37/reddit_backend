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
public interface CheckVotePostRepo extends JpaRepository<CheckVotePostEntity, Integer> {

    @Query("select t.vote_type from CheckVotePostEntity t where t.uid = :uid and t.post_id = :postId")
    public Optional<String> findVoteTypeByUidAndPostId(@Param("uid") int uid, @Param("postId") int postId);

    @Modifying
    @Query("update CheckVotePostEntity t set t.vote_type = :newVoteType where t.uid = :uid and t.post_id = :postId")
    public void updateVoteTypeByUidAndPostId(@Param("uid") int uid, @Param("postId") int postId, @Param("newVoteType") String newVoteType);

    @Modifying
    @Query("insert into CheckVotePostEntity (uid, post_id, vote_type) VALUES (:uid, :postId, :vote_type)")
    public Integer saveCheckVotePostEntity(@Param("uid") int uid, @Param("postId") int postId, @Param("vote_type") String vote_type);

}
