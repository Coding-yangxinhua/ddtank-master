package com.pwc.sdc.ddtank.exception;

import com.pwc.sdc.ddtank.dto.Response;
import lombok.Getter;

public class CallFailedException extends RuntimeException {
    // 失败原因
    @Getter
    private final Response response;

    public CallFailedException(Response reason) {
        this.response = reason;
    }
}
