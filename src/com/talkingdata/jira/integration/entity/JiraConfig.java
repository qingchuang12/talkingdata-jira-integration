package com.talkingdata.jira.integration.entity;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
@State(name = "JiraConfig", storages = {@Storage("talkingdataJiraSupportConfig.xml")})
public class JiraConfig implements PersistentStateComponent<JiraConfig> {
    private String jiraHome;
    private String username;
    private String password;
    private String cookie;
    private Integer boardId;
    private Integer sprintId;
    private String sprintName;

    public String getJiraHome() {
        return jiraHome;
    }

    public void setJiraHome(String jiraHome) {
        this.jiraHome = jiraHome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    @Nullable
    @Override
    public JiraConfig getState() {
        return this;
    }

    @Override
    public void loadState(JiraConfig jiraConfig) {
        XmlSerializerUtil.copyBean(jiraConfig, this);
    }

    @Nullable
    public static JiraConfig getInstance(Project project) {
        return ServiceManager.getService(project, JiraConfig.class);
    }
}
