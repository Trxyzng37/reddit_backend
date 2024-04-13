package com.trxyzng.trung.search.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityRepo extends JpaRepository<CommunityEntity, String> {
    public CommunityEntity save(CommunityEntity communityEntity);
    @Query("select t from CommunityEntity t where upper(t.name) like :name% order by t.subscriber_count desc , t.name asc limit :number")
    public Optional<CommunityEntity[]> findCommunityEntitiesByName(@Param("name") String name, @Param("number") int number);

    @Query("select t.icon_base64 from CommunityEntity t where t.name = :name")
    public String selectIconFromName(String name);
}
