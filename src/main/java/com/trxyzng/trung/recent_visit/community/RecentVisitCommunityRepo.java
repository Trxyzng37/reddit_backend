package com.trxyzng.trung.recent_visit.community;

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
public interface RecentVisitCommunityRepo extends JpaRepository<RecentVisitCommunityEntity, Integer> {
    @Query("select t.community_id from RecentVisitCommunityEntity t where t.uid = :uid order by t.visitted_time desc limit 5")
    int[] findByUid(@Param("uid") int uid);

    @Query("select case when count(t) > 0 then 1 else 0 end from RecentVisitCommunityEntity t where t.uid = :uid and t.community_id = :community_id")
    int existsByUidAndCommunityId(@Param("uid") int uid, @Param("community_id") int community_id);

    @Modifying
    @Query("update RecentVisitCommunityEntity t set t.visitted_time = :visitted_time where t.uid = :uid and t.community_id = :community_id")
    void updateVisittedTime(@Param("uid") int uid, @Param(("community_id")) int community_id, @Param("visitted_time") Instant visitted_time);
}
