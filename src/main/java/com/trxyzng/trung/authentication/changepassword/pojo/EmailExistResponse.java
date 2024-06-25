package com.trxyzng.trung.authentication.changepassword.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailExistResponse {
    @JsonProperty("emailExist")
    private boolean emailExist;
    @JsonProperty("googleEmail")
    private boolean googleEmail;
}
