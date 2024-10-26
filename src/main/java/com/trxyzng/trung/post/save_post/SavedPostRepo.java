package com.trxyzng.trung.post.save_post;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import com.trxyzng.trung.utility.PrimaryKey;

@Repository
@Transactional
public interface SavedPostRepo extends JpaRepository<SavedPostEntity, PrimaryKey> {

    @Query("select t.post_id from SavedPostEntity  t where t.uid = :uid and t.saved = 1 and t.deleted = 0 order by t.created_at desc")
    int[] getAllPostIdByUid(int uid);

    @Query("select t.saved from SavedPostEntity t where t.uid = :uid and t.post_id = :post_id")
    int selectSavedByUidAndPostId(@Param("uid") int uid, @Param("post_id") int post_id);

    @Query("select t.saved from SavedPostEntity t where t.uid = :uid and t.post_id = :post_id")
    Optional<Integer> selectSaveByUidAndPostId(@Param("uid") int uid, @Param("post_id") int post_id);

    @Query("select case when count(t) > 0 then 1 else 0 end from SavedPostEntity t where t.post_id = :post_id and t.uid = :uid")
    public int existsByPostIdAndUid(@Param("post_id") int post_id, @Param("uid") int uid);

    @Modifying
    @Query("update SavedPostEntity t set t.saved = :saved, t.created_at = :created_at where t.uid = :uid and t.post_id = :post_id")
    void updateByUidAndPostId(@Param("uid") int uid, @Param("post_id") int post_id, @Param("saved") int saved, @Param("created_at")Instant created_at);
}
