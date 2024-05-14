package com.trxyzng.trung.post.show_post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowPostService {
    @Autowired
    ShowPostRepo showPostRepo;

    public void saveOrUpdateShowPostEntity(int uid, int post_id, int show) {
        int exist = showPostRepo.existsByUidAndPostId(uid, post_id);
        if(exist == 0) {
            ShowPostEntity saved = showPostRepo.save(new ShowPostEntity(uid, post_id, show));
        }
        else {
            showPostRepo.updateShowPostEntityByUidAndPostId(uid, post_id, show);
        }
    }
}
