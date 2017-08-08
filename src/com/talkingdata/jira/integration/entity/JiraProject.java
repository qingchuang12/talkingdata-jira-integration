package com.talkingdata.jira.integration.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 开发：李冰心
 * 时间：2017年08月06日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class JiraProject {
    private String id;
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<JiraProject> parseList(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("values");
        if (jsonArray == null || jsonArray.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<JiraProject> list = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            list.add(parseListItem(item));
        }
        return list;
    }

    private static JiraProject parseListItem(JSONObject jsonObject){
        JiraProject project = new JiraProject();
        project.id = jsonObject.getString("id");
        project.key = jsonObject.getString("key");
        project.name = jsonObject.getString("name");
        return project;
    }
}
