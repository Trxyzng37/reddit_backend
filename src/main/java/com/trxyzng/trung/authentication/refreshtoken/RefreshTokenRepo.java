package com.trxyzng.trung.authentication.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Integer> {
    RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity);

//    @Query(nativeQuery = true, value = "SELECT SECURITY.is_id_in_tables(:id)")
//    boolean isIdExistInTables(@Param("id") int id);
}
