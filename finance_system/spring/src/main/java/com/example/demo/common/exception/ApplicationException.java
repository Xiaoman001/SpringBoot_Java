package com.example.demo.common.exception;


/**
 * @Description: 应用程序异常
 */
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = -6099409021619485349L;

    // 构造方法接收异常信息
    public ApplicationException() {
        super();
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

}
