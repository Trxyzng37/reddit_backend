package com.trxyzng.trung.post.show_post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trxyzng.trung.utility.PrimaryKey;

@Repository
@Transactional
public interface ShowPostRepo extends JpaRepository<ShowPostEntity, PrimaryKey> {

    @Modifying
    @Query("update ShowPostEntity t set t.show = :show where t.uid = :uid and t.post_id = :post_id")
    void updateShowPostEntityByUidAndPostId(@Param("uid") int uid, @Param("post_id") int post_id, @Param("show") int show);

    @Query("select case when count(t) > 0 then 1 else 0 end from ShowPostEntity t where t.uid = :uid and t.post_id = :post_id")
    int existsByUidAndPostId(@Param("uid") int uid, @Param("post_id") int post_id);


}
