package com.talkingdata.jira.integration.entity;

import java.util.List;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public abstract class AbstractResult<T extends AbstractEntity> {
    protected List<T> values;

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
