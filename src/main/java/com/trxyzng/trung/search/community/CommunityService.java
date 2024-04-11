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

    public CommunityEntity[] findCommunityEntitiesByName(String name, int number) {
        name = name.toUpperCase();
        return communityRepo.findCommunityEntitiesByName(name, number).orElse(new CommunityEntity[]{});
    }

//    public ArrayList<CommunityEntity> findAll() {
//        return communityRepo.findAll();
//    }
}
