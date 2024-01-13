package com.trxyzng.trung.user;

import com.trxyzng.trung.user.shared.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
