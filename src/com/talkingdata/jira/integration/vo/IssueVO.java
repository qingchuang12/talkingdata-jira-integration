package com.talkingdata.jira.integration.vo;

import com.talkingdata.jira.integration.entity.Issue;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 开发：李冰心
 * 时间：2017年08月04日
 * 邮箱：libingxin2013@outlook.com
 * 电话：156 5071 9638
 * 代码如果任何问题欢迎咨询
 */
public class IssueVO extends AbstractTableModel {
    private final static String[] columns = {"Key", "Status", "ET", "RT", "Summary"};
    private List<Issue> issues;
    private Object[][] data;

    public IssueVO(List<Issue> issues) {
        this.issues = issues;

        data = new Object[this.issues.size()][];
        for (int i = 0; i < issues.size(); i++) {
            data[i] = toData(issues.get(i));
        }
    }

    public Issue getSelectedIssueId(int index) {
        if (index >= 0 && index < data.length) {
            return issues.get(index);
        }
        return null;
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    //
    // This method is used by the JTable to define the default
    // renderer or editor for each cell. For example if you have
    // a boolean data it will be rendered as a check box. A
    // number value is right aligned.
    //
    @Override
    public Class getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }

    private Object[] toData(Issue issue) {
        return new Object[]{issue.getKey(), issue.getStatus(), issue.getEstimate(), issue.getRemaining(), issue.getSummary()};
    }
}
