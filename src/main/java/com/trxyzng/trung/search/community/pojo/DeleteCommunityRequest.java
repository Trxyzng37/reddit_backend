package com.trxyzng.trung.search.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class DeleteCommunityRequest {
    private int id;
    private int uid;
    private int deleted;
}
