package com.trxyzng.trung.edit_community.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EditCommunityRequest {
    private int community_id;
    private int uid;
    private String description;
    private String avatar;
    private String banner;
}
