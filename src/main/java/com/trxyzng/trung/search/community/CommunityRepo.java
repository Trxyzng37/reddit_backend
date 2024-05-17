package com.trxyzng.trung.search.community;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CommunityRepo extends JpaRepository<CommunityEntity, String> {
    public CommunityEntity save(CommunityEntity communityEntity);
    @Query("select t from CommunityEntity t where upper(t.name) like :name% order by t.subscriber_count desc , t.name asc limit :number")
    public Optional<CommunityEntity[]> findCommunityEntitiesByName(@Param("name") String name, @Param("number") int number);

    @Query("select t.name from CommunityEntity t where t.id = :id")
    public String selectNameFromId(int id);

    @Query("select t.avatar from CommunityEntity t where t.id = :id")
    public String selectIconFromId(int id);

    @Query("select case when count(t) > 0 then 1 else 0 end from CommunityEntity t where t.id = :id")
    int isCommunityEntityByUidExist(@Param("id") int id);

    @Query("select case when count(t) > 0 then 1 else 0 end from CommunityEntity t where t.name = :name")
    int isCommunityEntityByNameExist(@Param("name") String name);

    @Query("select t from CommunityEntity t where t.id = :id")
    CommunityEntity getCommunityEntityById(int id);

    @Modifying
    @Query("update CommunityEntity t set t.description = :description, t.avatar = :avatar, t.banner = :banner, t.scope = :scope where t.id = :id and t.uid = :uid")
    void updateCommunityEntity(@Param("id") int id, @Param("uid") int uid, @Param("description") String description, @Param("avatar") String avatar, @Param("banner") String banner, @Param("scope") int scope);
}
