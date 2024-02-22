package com.trxyzng.trung.authentication.shared.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PasscodeResponse {
    @JsonProperty("isPasscodeMatch")
    private boolean isPasscodeMatch;
    @JsonProperty("isPasscodeExpired")
    private boolean isPasscodeExpired;
}
