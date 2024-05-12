package com.trxyzng.trung.edit_community.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class EditCommunityResponse {
    @JsonProperty("editted")
    public boolean editted;
    @JsonProperty("error_code")
    public int error_code;
}
