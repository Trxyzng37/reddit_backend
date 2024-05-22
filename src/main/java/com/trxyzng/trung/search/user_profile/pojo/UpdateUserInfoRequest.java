package com.trxyzng.trung.search.user_profile.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserInfoRequest {
    private int uid;
    private String description;
    private String avatar;
}
