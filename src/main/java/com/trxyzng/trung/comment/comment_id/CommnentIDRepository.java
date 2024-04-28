package com.trxyzng.trung.comment.comment_id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommnentIDRepository extends JpaRepository<CommentID, Integer> {
    public CommentID save(CommentID commentID);
}
