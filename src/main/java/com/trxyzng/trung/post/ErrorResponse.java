package com.trxyzng.trung.post;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ErrorResponse {
    public int error_code;
    public String error_type;
    public String error_message;
}
