package com.trxyzng.trung.search.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    @Autowired
    CommunityRepo communityRepo;

    public void saveCommunityEntity(CommunityEntity communityEntity) {
        communityRepo.save(communityEntity);
    }

    public CommunityEntity[] findCommunityEntitiesByName(String name) {
        return communityRepo.findCommunityEntitiesByName(name).orElse(new CommunityEntity[]{});
    }
}
