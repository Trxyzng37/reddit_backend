package com.trxyzng.trung.search.user_profile;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserProfileRepo extends JpaRepository<UserProfileEntity, Integer> {
    @Query("select t from UserProfileEntity t where upper(t.username) like concat(upper(:name), '%') order by (t.post_karma + t.comment_karma) desc, t.username asc limit :number")
    Optional<UserProfileEntity[]> findUserProfileEntitiesByName(@Param("name") String name, @Param("number") int number);

    @Query("select t from UserProfileEntity t where upper(t.username) like concat('%', upper(:name), '%') order by (t.post_karma + t.comment_karma) desc , t.username asc limit :number")
    Optional<UserProfileEntity[]> findUserProfileEntitiesIncludeByName(@Param("name") String name, @Param("number") int number);

    @Query("select t.avatar from UserProfileEntity t where t.uid = :uid")
    String selectAvatarFromUid(int uid);

    @Query("select t.username from UserProfileEntity t where t.uid = :uid")
    String selectUsernameFromUid(int uid);

    @Query("select t.post_karma from UserProfileEntity t where t.uid = :uid")
    int selectPostKarmaFromUid(int uid);

    @Query("select t.comment_karma from UserProfileEntity t where t.uid = :uid")
    int selectCommentKarmaFromUid(int uid);

    @Modifying
    @Query("update UserProfileEntity t set t.post_karma = :post_karma where  t.uid = :uid")
    void updatePostKarmaByUid(@Param("uid") int uid, @Param("post_karma") int post_karma);

    @Modifying
    @Query("update UserProfileEntity t set t.comment_karma = :comment_karma where  t.uid = :uid")
    void updateCommentKarmaByUid(@Param("uid") int uid, @Param("comment_karma") int comment_karma);


    Optional<UserProfileEntity> findByUid(int uid);

    Optional<UserProfileEntity> findByUsername(String username);

    @Modifying
    @Query("update UserProfileEntity t set t.description = :description, t.avatar = :avatar where t.uid = :uid")
    void updateUserProfile(@Param("uid") int uid, @Param("description") String description, @Param("avatar") String avatar);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserProfileEntity t where t.uid = :uid")
    int existByUid(int uid);
}
