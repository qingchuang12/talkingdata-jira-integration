package com.talkingdata.jira.integration.service;

import com.talkingdata.jira.integration.HttpTool;

import java.util.Base64;
import java.util.HashMap;

/**
 * 开发：李冰心
 * 时间：2017年08月03日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class JiraService {
    private static final String COOKIE_NAME = "cookie";
    private static final HttpTool HTTP_TOOL;
    private static final HashMap<String, String> HEADER;

    static {
        HTTP_TOOL = new HttpTool();
        HEADER = new HashMap<>();
        HEADER.put("Content-Type", "application/json");
    }

    public static void main(String[] args){
        String home = "http://jira.tendcloud.com";
        login(home, "bingxin.li", "xin.3278");

        // 1. get board
//        String json = HTTP_TOOL.get(home + "/rest/agile/1.0/board", HEADER);
//        JSONObject jsonObject = JSON.parseObject(json);
//        System.out.println(JSON.toJSONString(jsonObject, true));
        // 2. get active sprint
//        String sprintJSON = HTTP_TOOL.get(home + "/rest/agile/1.0/board/5/sprint?state=active", HEADER);
//        System.out.println(JSON.toJSONString(JSON.parseObject(sprintJSON), true));

        // 2. get active sprint
//        String sprintJSON = HTTP_TOOL.get(home + "/rest/agile/1.0/board/5/project", HEADER);
//        System.out.println(JSON.toJSONString(JSON.parseObject(sprintJSON), true));


//        String sprintJSON = HTTP_TOOL.get(home + "/rest/agile/1.0/board/5/configuration", HEADER);
//        System.out.println(JSON.toJSONString(JSON.parseObject(sprintJSON), true));


//        String issueJSON = HTTP_TOOL.get(home + "/rest/agile/1.0/sprint/106/issue?maxResults=1", HEADER);
//        System.out.println(JSON.toJSONString(JSON.parseObject(issueJSON), true));
//
//        String issueJSON = HTTP_TOOL.get(home + "/rest/api/2/project/10002/statuses", HEADER);
//        System.out.println(JSON.toJSONString(JSON.parseObject(issueJSON), true));

//        String issueJSON = HTTP_TOOL.get(home + "/rest/api/2/project/SDMK/statuses", HEADER);
//        System.out.println(JSON.toJSONString(JSON.parseObject(issueJSON), true));
    }

    private static void login(String home, String user, String password){
//        String loginUrl = home + "/rest/auth/1/session";
//        String credentials = new JSONObject()
//                .fluentPut("username", user)
//                .fluentPut("password", password)
//                .toJSONString();
//
//        String json = HTTP_TOOL.post(loginUrl, HEADER, credentials);
//        JSONObject jsonObject = JSON.parseObject(json).getJSONObject("session");
//        HEADER.put("cookie", jsonObject.getString("name") + "=" + jsonObject.getString("value"));
        System.out.println(Base64.getEncoder().encodeToString(String.format("%s:%s","bingxin.li", "xin.3278").getBytes()).replace("\n", ""));

    }

}
