package com.trxyzng.trung.authentication.refreshtoken;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Integer> {
    @Query("select r from RefreshTokenEntity as r where r.refresh_token = :refresh_token")
    RefreshTokenEntity findByRefreshToken(@Param("refresh_token") String refresh_token);

    ArrayList<RefreshTokenEntity> findRefreshTokenById(int id);
}
