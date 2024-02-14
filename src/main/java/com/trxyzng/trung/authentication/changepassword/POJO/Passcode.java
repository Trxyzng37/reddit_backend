package com.trxyzng.trung.authentication.changepassword.POJO;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Passcode {
    private String email;
    private int passcode;
    private Instant sendAt;
}
