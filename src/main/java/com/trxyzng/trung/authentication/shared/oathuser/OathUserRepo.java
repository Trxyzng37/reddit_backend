package com.trxyzng.trung.authentication.shared.oathuser;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface OathUserRepo extends JpaRepository<OathUserEntity, Integer> {
    Optional<OathUserEntity> findByEmail(String email);

    OathUserEntity save(OathUserEntity oathUserEntity);

    @Query("select 1 from OathUserEntity t where t.email = :email")
    boolean isEMailExistInDB(@Param("email") String email);
}
