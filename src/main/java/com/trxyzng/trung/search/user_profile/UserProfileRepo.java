package com.trxyzng.trung.search.user_profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfileEntity, Integer> {
    @Query("select t from UserProfileEntity t where upper(t.name) like :name% order by t.karma desc , t.name asc limit :number")
    public Optional<UserProfileEntity[]> findUserProfileEntitiesByName(@Param("name") String name, @Param("number") int number);
//    public Optional<UserProfileEntity[]> findByNameStartingWith(@Param("name") String name);
}
