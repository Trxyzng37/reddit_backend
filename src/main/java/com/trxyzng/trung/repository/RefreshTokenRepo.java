package com.trxyzng.trung.repository;

import com.trxyzng.trung.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    @Query("select r from RefreshToken as r where r.refresh_token = :refresh_token")
    RefreshToken findByRefreshToken(@Param("refresh_token") String refresh_token);

    ArrayList<RefreshToken> findRefreshTokenById(int id);
}
