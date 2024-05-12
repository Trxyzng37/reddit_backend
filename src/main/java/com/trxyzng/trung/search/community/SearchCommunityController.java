package com.trxyzng.trung.search.community;

import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchCommunityController {
    @Autowired
    CommunityService communityService;

    @RequestMapping(value = "/find-community", method = RequestMethod.GET)
    public ResponseEntity<String> findCommunitiesByName(@RequestParam("name") String name) {
        CommunityEntity[] result = communityService.findCommunityEntitiesByName(name, 4);
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "get-community-info", method = RequestMethod.GET)
    public ResponseEntity<String> findCommunityInfoById(@RequestParam("id") int id) {
        System.out.println("dd");
        CommunityEntity result = communityService.getCommunityEntityById(id);
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}
