package com.talkingdata.jira.integration.exception;


/**
 * 开发：李冰心
 * 时间：2017年04月01日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class HttpInternalServerException extends HttpException {
    private static final long serialVersionUID = -8944779541654985084L;

    @Override
    public int httpStatus() {
        return HttpException.HTTP_STATUS_INTERNAL_SERVER_ERROR;
    }

    public HttpInternalServerException() {
        super();
    }

    public HttpInternalServerException(String message) {
        super(message);
    }

    public HttpInternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpInternalServerException(Throwable cause) {
        super(cause);
    }

    public HttpInternalServerException(String message, Throwable cause,
                                          boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
