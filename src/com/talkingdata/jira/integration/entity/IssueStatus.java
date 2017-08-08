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
public class IssueStatus {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<IssueStatus> parseList(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject columnConfig = jsonObject.getJSONObject("columnConfig");
        if (columnConfig == null) {
            return Collections.EMPTY_LIST;
        }

        JSONArray columns = columnConfig.getJSONArray("columns");
        if (columns == null || columns.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<IssueStatus> list = new ArrayList<>(columns.size());
        for (int i = 0; i < columns.size(); i++) {
            JSONObject column = columns.getJSONObject(i);
            IssueStatus issueStatus = new IssueStatus();
            issueStatus.setName(column.getString("name"));

            JSONArray statuses = column.getJSONArray("statuses");
            if(statuses != null && statuses.size() > 0){
                issueStatus.setId(statuses.getJSONObject(0).getString("id"));
            }
            list.add(issueStatus);
        }
        return list;
    }
}

