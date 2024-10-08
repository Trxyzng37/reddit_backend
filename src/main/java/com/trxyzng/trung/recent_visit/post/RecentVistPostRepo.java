package com.trxyzng.trung.recent_visit.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;

@Repository
@Transactional
public interface RecentVistPostRepo extends JpaRepository<RecentVisitPostEntity, PrimaryKey> {
    @Query("select t.post_id from RecentVisitPostEntity t where t.uid = :uid order by t.visitted_time desc limit 10")
    int[] findByUid(@Param("uid") int uid);

    @Query("select case when count(t) > 0 then 1 else 0 end from RecentVisitPostEntity t where t.uid = :uid and t.post_id = :post_id")
    int existsByUidAndPostId(@Param("uid") int uid, @Param("post_id") int post_id);

    @Modifying
    @Query("update RecentVisitPostEntity t set t.visitted_time = :visitted_time where t.uid = :uid and t.post_id = :post_id")
    void updateVisittedTime(@Param("uid") int uid, @Param(("post_id")) int post_id, @Param("visitted_time") Instant visitted_time);
}
