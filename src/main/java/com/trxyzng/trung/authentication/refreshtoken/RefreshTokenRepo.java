package com.trxyzng.trung.authentication.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Integer> {
    RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity);

    @Query("select case when count(t) > 0 then 1 else 0 end from RefreshTokenEntity t where t.refresh_token = :refresh_token")
    int isRefreshTokenExistInDb(@Param("refresh_token") String refresh_token);

    @Query("select t.uid from RefreshTokenEntity t where t.refresh_token = :token")
    int selectUidByRefreshToken(String token);

//    @Query(nativeQuery = true, value = "SELECT SECURITY.is_id_in_tables(:id)")
//    boolean isIdExistInTables(@Param("id") int id);
}
