package com.trxyzng.trung.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class DefaultResponse {
    @JsonProperty("error_code")
    public int error_code;
    @JsonProperty("error_description")
    public String error_description;
}
