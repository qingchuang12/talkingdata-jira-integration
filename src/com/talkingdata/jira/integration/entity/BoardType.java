package com.talkingdata.jira.integration.entity;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public enum BoardType {
    Scrum("scrum"),
    Kanban("kanban");
    private String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
