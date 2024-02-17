package com.trxyzng.trung.authentication.signin.google;

import com.trxyzng.trung.authentication.shared.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface OathUserRepo extends JpaRepository<OathUserEntity, Integer> {
    Optional<OathUserEntity> findByEmail(String email);
}
