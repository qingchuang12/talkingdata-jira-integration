package com.talkingdata.jira.integration.service;

import com.talkingdata.jira.integration.entity.Board;
import com.talkingdata.jira.integration.entity.Issue;
import com.talkingdata.jira.integration.entity.IssueStatus;
import com.talkingdata.jira.integration.entity.Sprint;

import java.util.List;
import java.util.Map;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public interface JiraFacade {
    String login(String home, String username, String password);

    List<Board> getBoards(String home,  String cookie);

    List<IssueStatus> getStatuses(String home,  String cookie, Integer boardId);

    List<Sprint> getActiveSprints(String home,  String cookie, Integer boardId);

    List<Issue> getIssues(String username, String home,  String cookie, Integer sprintId);

    void logWork(String home,  String cookie, String issueId, String timeSpent, String comment);

    Map<String, String> getTransitionMap(String home,  String cookie, String issueId);

    void changeIssueStatus(String home,  String cookie, String issueId, String issueStatusId);
}
