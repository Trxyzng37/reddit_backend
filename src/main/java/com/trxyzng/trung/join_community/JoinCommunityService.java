package com.trxyzng.trung.join_community;

import com.trxyzng.trung.join_community.pojo.JoinCommunityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinCommunityService {
    @Autowired
    JoinCommunityRepo joinCommunityRepo;

    public void saveOrUpdateJoinCommunityEntity(int uid, int community_id, int subscribed) {
        int isSubscribed = joinCommunityRepo.isJoinCommunityEntityByUidAndCommunityIdExist(uid, community_id);
        if(isSubscribed == 0) {
            JoinCommunityEntity saved = joinCommunityRepo.save(new JoinCommunityEntity(uid, community_id, subscribed));
            System.out.println("Save join community with uid: "+uid+" and cid: "+community_id+" and subcribed: "+subscribed);
        }
        else {
            joinCommunityRepo.updateJoinCommunityEntityByUidAndCommunityId(uid, community_id, subscribed);
            System.out.println("Update join community with uid: "+uid+" and cid: "+community_id+" and subcribed: "+subscribed);
        }
    }

    public int getSubscribedStatus(int uid, int community_id) {
        int exist = joinCommunityRepo.isJoinCommunityEntityByUidAndCommunityIdExist(uid, community_id);
        if(exist == 0) {
            return 0;
        }
        return joinCommunityRepo.getSubscribedStatusFromJoinCommunityEntity(uid, community_id);
    }
}
