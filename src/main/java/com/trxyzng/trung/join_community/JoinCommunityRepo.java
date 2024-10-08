package com.trxyzng.trung.join_community;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface JoinCommunityRepo extends JpaRepository<JoinCommunityEntity, PrimaryKey> {

    JoinCommunityEntity save(JoinCommunityEntity joinCommunityEntity);

    @Query("select t.community_id from JoinCommunityEntity t where t.uid = :uid and t.subscribed = 1")
    int[] findAllJoinedCommunitiesByUid(@Param("uid") int uid);

    @Query("select case when count(t) > 0 then 1 else 0 end from JoinCommunityEntity t where t.uid = :uid and t.community_id = :community_id")
    int isJoinCommunityEntityByUidAndCommunityIdExist(@Param("uid") int uid, @Param("community_id") int community_id);

    @Query("select t.subscribed from JoinCommunityEntity  t where t.uid = :uid and t.community_id = :community_id")
    int getSubscribedStatusFromJoinCommunityEntity(@Param("uid") int uid, @Param("community_id") int community_id);

    @Modifying
    @Query("update JoinCommunityEntity t set t.subscribed = :subscribed  where t.uid = :uid and t.community_id = :community_id")
    void updateJoinCommunityEntityByUidAndCommunityId(@Param("uid") int uid, @Param("community_id") int community_id, @Param("subscribed") int subscribed);
}
