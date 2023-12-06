package com.trxyzng.trung.repository;

import com.trxyzng.trung.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
