package com.trxyzng.trung.join_community;

import com.trxyzng.trung.search.community.CommunityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinCommunityService {
    @Autowired
    JoinCommunityRepo joinCommunityRepo;
    @Autowired
    CommunityRepo communityRepo;

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
        int subscriber_count = communityRepo.selectSubscribeCountFromId(community_id);
        System.out.println("Before join: "+subscriber_count);
        if(subscribed == 0) {
            communityRepo.updateSubscribedById(community_id, subscriber_count - 1);
        }
        else {
            communityRepo.updateSubscribedById(community_id, subscriber_count + 1);
        }
        System.out.println("after join: "+communityRepo.selectSubscribeCountFromId(community_id));
    }

    public int getSubscribedStatus(int uid, int community_id) {
        int exist = joinCommunityRepo.isJoinCommunityEntityByUidAndCommunityIdExist(uid, community_id);
        if(exist == 0) {
            return 0;
        }
        return joinCommunityRepo.getSubscribedStatusFromJoinCommunityEntity(uid, community_id);
    }

    public int[] findAllJoinedCommunitiesByUid(int uid) {
        return joinCommunityRepo.findAllJoinedCommunitiesByUid(uid);
    }
}
