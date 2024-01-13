package com.trxyzng.trung.authentication.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Integer> {
//    @Query("insert into SECURITY.refresh_token values (:uid, :refresh_token)")
//    RefreshTokenEntity savet(@Param("uid") int uid, @Param("refresh_token") String refresh_token);
    RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity);
}
