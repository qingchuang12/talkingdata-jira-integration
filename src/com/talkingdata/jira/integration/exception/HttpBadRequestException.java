package com.talkingdata.jira.integration.exception;


/**
 * 开发：李冰心
 * 时间：2017年04月01日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class HttpBadRequestException extends HttpException {
    private static final long serialVersionUID = 4573827434593303712L;

    @Override
    public int httpStatus() {
        return HttpException.HTTP_STATUS_BAD_REQUEST;
    }

    public HttpBadRequestException() {
    }

    public HttpBadRequestException(String message) {
        super(message);
    }

    public HttpBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpBadRequestException(Throwable cause) {
        super(cause);
    }

    public HttpBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
