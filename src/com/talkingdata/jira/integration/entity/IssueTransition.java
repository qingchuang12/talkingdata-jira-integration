package com.talkingdata.jira.integration.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * 开发：李冰心
 * 时间：2017年08月06日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class IssueTransition {
    private String id;
    private String issueStatusId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueStatusId() {
        return issueStatusId;
    }

    public void setIssueStatusId(String issueStatusId) {
        this.issueStatusId = issueStatusId;
    }

    public static Map<String, String> parse(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray transitions = jsonObject.getJSONArray("transitions");
        if (transitions == null || transitions.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        Map<String, String> map = new HashMap<>(transitions.size());
        List<IssueStatus> list = new ArrayList<>(transitions.size());
        for (int i = 0; i < transitions.size(); i++) {
            JSONObject transition = transitions.getJSONObject(i);
            JSONObject toStatus = transition.getJSONObject("to");
            map.put(toStatus.getString("id"), transition.getString("id"));
        }
        return map;
    }
}
