package com.trxyzng.trung.search.user_profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfileEntity, Integer> {
    @Query("select t from UserProfileEntity t where upper(t.username) like concat(upper(:name), '%') order by (t.post_karma + t.comment_karma) desc, t.username asc limit :number")
    Optional<UserProfileEntity[]> findUserProfileEntitiesByName(@Param("name") String name, @Param("number") int number);

    @Query("select t from UserProfileEntity t where upper(t.username) like concat('%', upper(:name), '%') order by (t.post_karma + t.comment_karma) desc , t.username asc limit :number")
    Optional<UserProfileEntity[]> findUserProfileEntitiesIncludeByName(@Param("name") String name, @Param("number") int number);

    @Query("select t.avatar from UserProfileEntity t where t.uid = :uid")
    String selectAvatarFromUid(int uid);

    Optional<UserProfileEntity> findByUid(int uid);

    Optional<UserProfileEntity> findByUsername(String username);
}
