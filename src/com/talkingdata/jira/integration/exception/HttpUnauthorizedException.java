package com.talkingdata.jira.integration.exception;


/**
 * 开发：李冰心
 * 时间：2017年04月01日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class HttpUnauthorizedException extends HttpException {
    private static final long serialVersionUID = -5649587853960404893L;

    @Override
    public int httpStatus() {
        return HttpException.HTTP_STATUS_UNAUTHORIZED;
    }

    public HttpUnauthorizedException() {
    }

    public HttpUnauthorizedException(String message) {
        super(message);
    }

    public HttpUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpUnauthorizedException(Throwable cause) {
        super(cause);
    }

    public HttpUnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
