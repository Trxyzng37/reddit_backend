package com.trxyzng.trung.authentication.shared.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity, Integer> {
//    @Query("select uid, username, password, email from User.users where username = :name")
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(int id);
    UserEntity save(UserEntity userEntity);
}
