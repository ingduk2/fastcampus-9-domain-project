package org.fastcampus.common.ui;

import org.fastcampus.common.domain.exception.ErrorCode;

public record Response<T>(
        Integer code,
        String message,
        T value
) {

    public static <T> Response<T> ok() {
        return new Response<>(0, "ok", null);
    }

    public static <T> Response<T> ok(T value) {
        return new Response<>(0, "ok", value);
    }

    public static <T> Response<T> error(ErrorCode errorCode) {
        return new Response<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
