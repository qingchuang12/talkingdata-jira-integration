package com.talkingdata.jira.integration.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talkingdata.jira.integration.HttpTool;
import com.talkingdata.jira.integration.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class JiraFacadeImpl implements JiraFacade {
    private static final HttpTool HTTP_TOOL = new HttpTool();

    @Override
    public List<Board> getBoards(String home, String cookie) {
        String json = HTTP_TOOL.get(home + "/rest/agile/1.0/board?type=scrum", cookie);
        BoardResult boardResult = JSON.parseObject(json, BoardResult.class);
        return boardResult.getValues();
    }

    @Override
    public List<IssueStatus> getStatuses(String home, String cookie, Integer boardId) {
        StringBuilder sb = new StringBuilder(home)
                .append("/rest/agile/1.0/board/")
                .append(boardId)
                .append("/configuration");

        String json = HTTP_TOOL.get(sb.toString(), cookie);
        return IssueStatus.parseList(json);
    }

    @Override
    public List<Sprint> getActiveSprints(String home, String cookie, Integer boardId) {
        StringBuilder sb = new StringBuilder(home)
                .append("/rest/agile/1.0/board/")
                .append(boardId)
                .append("/sprint?state=active");

        String json = HTTP_TOOL.get(sb.toString(), cookie);
        SprintResult sprintResult = JSON.parseObject(json, SprintResult.class);
        return sprintResult.getValues();
    }

    @Override
    public List<Issue> getIssues(String username, String home, String cookie, Integer sprintId) {
        StringBuilder sb = new StringBuilder(home)
                .append("/rest/agile/1.0/sprint/")
                .append(sprintId)
                .append("/issue?maxResults=1000");

        String json = HTTP_TOOL.get(sb.toString(), cookie);

        List<Issue> originalList = Issue.parseList(json);
        List<Issue> listFiltered = originalList
                .stream()
                .filter(r -> r.getEmail().startsWith(username))
                .sorted((r1, r2) -> r1.getId().compareTo(r2.getId()))
                .collect(Collectors.toList());
        return listFiltered;
    }

    @Override
    public void logWork(String home, String cookie, String issueId, String timeSpent, String comment) {
        StringBuilder sb = new StringBuilder(home)
                .append("/rest/api/2/issue/")
                .append(issueId)
                .append("/worklog");

        String json = new JSONObject()
                .fluentPut("timeSpentSeconds", convertToSeconds(timeSpent))
                .fluentPut("comment", StringUtils.isEmpty(comment) ? "" : comment)
                .toJSONString();

        HTTP_TOOL.post(sb.toString(), cookie, json);
    }

    @Override
    public Map<String, String> getTransitionMap(String home, String cookie, String issueId) {
        StringBuilder sb = new StringBuilder(home)
                .append("/rest/api/2/issue/")
                .append(issueId)
                .append("/transitions?expand=transitions.fields");

        String json = HTTP_TOOL.get(sb.toString(), cookie);
        return IssueTransition.parse(json);
    }

    @Override
    public void changeIssueStatus(String home, String cookie, String issueId, String issueStatusId) {
        Map<String, String> transitionMap = getTransitionMap(home, cookie, issueId);
        String transitionId = transitionMap.get(issueStatusId);

        StringBuilder sb = new StringBuilder(home)
                .append("/rest/api/2/issue/")
                .append(issueId)
                .append("/transitions");

        String json = new JSONObject()
                .fluentPut("transition", new JSONObject().fluentPut("id", transitionId))
                .toJSONString();

        HTTP_TOOL.post(sb.toString(), cookie, json);
    }

    private long convertToSeconds(String timeSpent) {
        String lastChar = timeSpent.substring(timeSpent.length() - 1).toLowerCase();
        String prefixChar = timeSpent.substring(0, timeSpent.length() - 1);
        Double prefixValue = Double.valueOf(prefixChar);
        if (lastChar.equals("d")) {
            return new Double(prefixValue * 24 * 60 * 60).longValue();
        } else if (lastChar.equals("h")) {
            return new Double(prefixValue * 60 * 60).longValue();
        } else if (lastChar.equals("m")) {
            return new Double(prefixValue * 60).longValue();
        } else if (lastChar.equals("s")) {
            return new Double(prefixValue).longValue();
        }
        return Long.valueOf(timeSpent);
    }

    public String login(String home, String username, String password) {
        String loginUrl = home + "/rest/auth/1/session";
        String credentials = new JSONObject()
                .fluentPut("username", username)
                .fluentPut("password", password)
                .toJSONString();

        String json = HTTP_TOOL.post(loginUrl, null, credentials);
        JSONObject jsonObject = JSON.parseObject(json).getJSONObject("session");
        return jsonObject.getString("name") + "=" + jsonObject.getString("value");
    }
}
