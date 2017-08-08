package com.talkingdata.jira.integration.ui;

import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.talkingdata.jira.integration.entity.Issue;
import com.talkingdata.jira.integration.entity.IssueStatus;
import com.talkingdata.jira.integration.entity.JiraConfig;
import com.talkingdata.jira.integration.service.JiraFacade;
import com.talkingdata.jira.integration.service.JiraFacadeImpl;
import com.talkingdata.jira.integration.vo.IssueVO;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey.Chursin
 * Date: Aug 25, 2010
 * Time: 2:09:00 PM
 */
public class MyToolWindowFactory extends JDialog implements ToolWindowFactory {
    private JPanel myToolWindowContent;
    private JTable tableIssues;
    private JTextField tfSpent;
    private JComboBox cbStatus;
    private JButton settings;
    private JTextArea taComment;
    private JButton buttonSubmit;
    private JButton buttonChange;
    private ToolWindow myToolWindow;
    private JiraConfig config;
    private JiraFacade jiraFacade;
    private List<IssueStatus> issueStatuses;
    private List<Issue> issueList;
    private String selectedIssueId;
    private String cookie;
    private ListSelectionListener listSelectionListener;
    private Project project;

    public MyToolWindowFactory() {
        jiraFacade = new JiraFacadeImpl();

        buttonSubmit.addActionListener(
                (e) -> {
                    if (StringUtils.isEmpty(tfSpent.getText())) {
                        return;
                    }
                    jiraFacade.logWork(config.getJiraHome(), cookie, selectedIssueId, tfSpent.getText(), taComment.getText());

                    this.tfSpent.setText("");
                    this.taComment.setText("");
                    loadIssues();
                }
        );

        buttonChange.addActionListener(
                (e) -> {
                    int index = cbStatus.getSelectedIndex();
                    if (issueStatuses != null
                            && index >= 0
                            && index < issueStatuses.size()) {
                        IssueStatus issueStatus = issueStatuses.get(index);
                        jiraFacade.changeIssueStatus(config.getJiraHome(), cookie, selectedIssueId, issueStatus.getId());
                        loadIssues();
                    }
                }
        );

        listSelectionListener = (e) -> {
            int index = tableIssues.getSelectedRow();
            selectedIssueId = null;
            if (tableIssues.getModel() instanceof IssueVO) {
                IssueVO issueVO = (IssueVO) tableIssues.getModel();
                Issue issue = issueVO.getSelectedIssueId(index);
                if (issue != null) {
                    selectedIssueId = issue.getId();
                    this.cbStatus.setSelectedItem(issue.getStatus());
                }
            }
        };
        this.tableIssues.getSelectionModel().addListSelectionListener(
                (e) -> {
                    int index = tableIssues.getSelectedRow();
                    if (index < 0) {
                        return;
                    }
                    selectedIssueId = null;
                    if (tableIssues.getModel() instanceof IssueVO) {
                        IssueVO issueVO = (IssueVO) tableIssues.getModel();
                        Issue issue = issueVO.getSelectedIssueId(index);
                        if (issue != null) {
                            selectedIssueId = issue.getId();
                            this.cbStatus.setSelectedItem(issue.getStatus());
                        }
                    }
                }
        );

        this.settings.addActionListener(
                (e) -> {
                    ShowSettingsUtil.getInstance().showSettingsDialog(project, "this");
                }
        );

    }

    private void loadIssues() {
        this.tableIssues.getSelectionModel().removeListSelectionListener(listSelectionListener);
        issueList = jiraFacade.getIssues(config.getUsername(), config.getJiraHome(), cookie, config.getSprintId());
        tableIssues.setModel(new IssueVO(issueList));
        this.tableIssues.getSelectionModel().addListSelectionListener(listSelectionListener);
        tableIssues.getColumnModel().getColumn(0).setMinWidth(90);
        tableIssues.getColumnModel().getColumn(0).setMaxWidth(90);
        tableIssues.getColumnModel().getColumn(1).setMinWidth(90);
        tableIssues.getColumnModel().getColumn(1).setMaxWidth(90);
        tableIssues.getColumnModel().getColumn(2).setMinWidth(40);
        tableIssues.getColumnModel().getColumn(2).setMaxWidth(40);
        tableIssues.getColumnModel().getColumn(3).setMinWidth(40);
        tableIssues.getColumnModel().getColumn(3).setMaxWidth(40);
        tableIssues.getColumnModel().getColumn(4).setMinWidth(180);
        tableIssues.getColumnModel().getColumn(4).setMaxWidth(Integer.MAX_VALUE);
        if (issueList.size() > 0) {
            for (int i = 0; i < issueList.size(); i++) {
                Issue issue = issueList.get(i);
                if (issue.getId().equals(selectedIssueId)) {
                    tableIssues.setRowSelectionInterval(i, i);
                    return;
                }
            }
            tableIssues.setRowSelectionInterval(0, 0);
        }
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.project = project;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
        config = JiraConfig.getInstance(project);

        if (StringUtils.isNotEmpty(config.getJiraHome())
                && StringUtils.isNotEmpty(config.getUsername())
                && StringUtils.isNotEmpty(config.getPassword())) {
            cookie = jiraFacade.login(config.getJiraHome(), config.getUsername(), config.getPassword());
            if (StringUtils.isNotEmpty(cookie)) {
                // 1. 获取所有的issue
                loadIssues();

                // 1. 获取所有的状态
                issueStatuses = jiraFacade.getStatuses(config.getJiraHome(), cookie, config.getBoardId());
                if (issueStatuses != null && issueStatuses.size() > 0) {
                    issueStatuses.forEach(r -> cbStatus.addItem(r.getName()));
                    if (issueList != null && issueList.size() > 0) {
                        cbStatus.setSelectedItem(issueList.get(0).getStatus());
                    }
                }

            }
        }
    }
}
