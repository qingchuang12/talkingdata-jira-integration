package com.talkingdata.jira.integration.exception;

/**
 * 开发：李冰心
 * 时间：2017年04月01日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public abstract class HttpException extends RuntimeException {
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_CREATED = 201;
    public static final int HTTP_STATUS_ACCEPTED = 202;
    public static final int HTTP_STATUS_NO_CONTENT = 204;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_UNAUTHORIZED = 401;
    public static final int HTTP_STATUS_FORBIDDEN = 403;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;

    public abstract int httpStatus();

    protected HttpException() {
        super();
    }

    protected HttpException(String message) {
        super(message);
    }

    protected HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    protected HttpException(Throwable cause) {
        super(cause);
    }

    protected HttpException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return new StringBuilder("{\"status\": ")
                .append(httpStatus())
                .append(", \"msg\":\"")
                .append(getMessage())
                .append("\"}")
                .toString();
    }
}
