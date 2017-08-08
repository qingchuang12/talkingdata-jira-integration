package com.talkingdata.jira.integration;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 开发：李冰心
 * 时间：2017年04月27日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class HttpTool {
    public static final CloseableHttpClient client = HttpClients.createDefault();


    public String get(String url) {
        return execute(new HttpGet(url));
    }


    public String get(String url, String cookie) {
        return execute(build(new HttpGet(url), cookie));
    }

    public String post(String url) {
        return execute(new HttpPost(url));
    }


    public String post(String url, String cookie) {
        return execute(build(new HttpPost(url), cookie));
    }


    public String post(String url, String cookie, String json) {
        return execute(build(new HttpPost(url), cookie, json));
    }


    public String put(String url, String cookie) {
        return execute(build(new HttpPut(url), cookie));
    }


    public String put(String url, String cookie, String json) {
        return execute(build(new HttpPut(url), cookie, json));
    }


    public String delete(String url, String cookie) {
        return execute(build(new HttpDelete(url), cookie));
    }

    protected HttpRequestBase build(HttpRequestBase httpRequestBase, String cookie) {
        httpRequestBase.addHeader("Content-Type", "application/json");
        if (StringUtils.isNotEmpty(cookie)) {
            httpRequestBase.addHeader("cookie", cookie);
        }
        return httpRequestBase;
    }

    protected HttpRequestBase build(HttpEntityEnclosingRequestBase httpRequestBase, String cookie, String json) {
        build(httpRequestBase, cookie);
        if (StringUtils.isNotEmpty(json)) {
            httpRequestBase.setEntity(new StringEntity(json, "UTF-8"));
        }
        return httpRequestBase;
    }

    protected String execute(HttpRequestBase httpRequest) {
        try (CloseableHttpResponse response = client.execute(httpRequest)) {
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
