package com.trxyzng.trung.create_community.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateCommunityRequest {
    private int uid;
    private String name;
    private String description;
    private String avatar;
    private String banner;
    private int scope;
}
