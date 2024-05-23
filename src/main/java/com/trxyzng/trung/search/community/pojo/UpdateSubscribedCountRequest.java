package com.trxyzng.trung.search.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateSubscribedCountRequest {
    private int id;
    private int count;
}
