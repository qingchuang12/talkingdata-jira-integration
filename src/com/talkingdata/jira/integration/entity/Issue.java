package com.talkingdata.jira.integration.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class Issue {
    private String id;
    private String key;
    private String summary;
    private String created;
    private String priority;
    private String email;
    private String updated;
    private String status;
    private String remaining;
    private String estimate;
    private String issueTypeId;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(String issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public static List<Issue> parseList(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray issues = jsonObject.getJSONArray("issues");
        if (issues == null || issues.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<Issue> list = new ArrayList<>(issues.size());
        for (int i = 0; i < issues.size(); i++) {
            JSONObject item = issues.getJSONObject(i);
            list.add(parseListItem(item));
        }
        return list;
    }

    private static Issue parseListItem(JSONObject jsonObject) {
        Issue issue = new Issue();
        issue.id = jsonObject.getString("id");
        issue.key = jsonObject.getString("key");

        JSONObject fields = jsonObject.getJSONObject("fields");
        issue.summary = fields.getString("summary");
        issue.email = fields.getJSONObject("assignee").getString("emailAddress");
        issue.status = fields.getJSONObject("status").getString("name");
        JSONObject timeTracking = fields.getJSONObject("timetracking");
        issue.remaining = timeTracking.getString("remainingEstimate");
        issue.estimate = timeTracking.getString("originalEstimate");
        return issue;
    }

    public static Issue parse(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Issue issue = new Issue();
        issue.id = jsonObject.getString("id");

        JSONObject fields = jsonObject.getJSONObject("fields");
        issue.status = fields.getJSONObject("status").getString("name");

        JSONObject timeTracking = fields.getJSONObject("timetracking");
        issue.remaining = timeTracking.getString("remainingEstimate");
        issue.estimate = timeTracking.getString("originalEstimate");
        return issue;
    }

    private static String parseTime(String time) throws Exception {
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = utcFormat.parse(time);
        DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return localFormat.format(date);
    }
}
