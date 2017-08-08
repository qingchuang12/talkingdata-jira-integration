package com.talkingdata.jira.integration.ui;

import com.intellij.openapi.project.Project;
import com.talkingdata.jira.integration.entity.Board;
import com.talkingdata.jira.integration.entity.JiraConfig;
import com.talkingdata.jira.integration.entity.Sprint;
import com.talkingdata.jira.integration.service.JiraFacade;
import com.talkingdata.jira.integration.service.JiraFacadeImpl;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

public class MainForm extends JDialog {
    private JPanel contentPane;
    private JTextField fieldJiraHome;
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
    private JLabel labelSprint;
    private JComboBox cbBoards;

    private JiraConfig config;
    private JiraFacade jiraFacade;
    private Sprint selectedSprint;
    private Integer selectedBoardId;
    private String cookie;
    private List<Board> boards;

    public MainForm() {
        setContentPane(contentPane);
        jiraFacade = new JiraFacadeImpl();
        boards = new ArrayList<>();
    }

    public void createUI(Project project) {
        config = JiraConfig.getInstance(project);
        reset();

        this.buttonLogin.addActionListener(
                (e) -> {
                    loadData();
                }
        );

        cbBoards.addItemListener(
                (e) -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        for (Board board : boards) {
                            if (board.getName().equals(String.valueOf(e.getItem()))) {
                                selectedBoardId = board.getId();

                                selectedSprint = null;
                                List<Sprint> list = jiraFacade.getActiveSprints(fieldJiraHome.getText(), cookie, selectedBoardId);
                                if (list != null && list.size() > 0) {
                                    this.labelSprint.setForeground(Color.BLACK);
                                    selectedSprint = list.get(0);
                                    this.labelSprint.setText(selectedSprint.getName());
                                } else {
                                    this.labelSprint.setForeground(Color.RED);
                                    this.labelSprint.setText("The active labelSprint not found.");
                                }
                            }
                        }
                    }
                }
        );

        loadData();
    }

    private void loadData() {
        if (StringUtils.isNotEmpty(fieldJiraHome.getText())
                && StringUtils.isNotEmpty(fieldUsername.getText())
                && fieldPassword.getPassword() != null
                && fieldPassword.getPassword().length > 0) {
            cookie = jiraFacade.login(fieldJiraHome.getText(), fieldUsername.getText(), new String(fieldPassword.getPassword()));
            if (StringUtils.isNotEmpty(cookie)) {
                loadData(fieldJiraHome.getText(), cookie);
            }
        }
    }

    private void loadData(String home, String cookie) {
        cbBoards.removeAllItems();
        boards.clear();
        List<Board> boardList = jiraFacade.getBoards(home, cookie);
        if (boardList != null) {
            boardList.forEach(r -> {
                boards.add(r);
                cbBoards.addItem(r.getName());
            });
        }
    }

    public boolean isModified() {
        if (isModified(this.fieldJiraHome.getText(), config.getJiraHome())) {
            return true;
        }

        if (isModified(this.fieldUsername.getText(), config.getUsername())) {
            return true;
        }

        if (isModified(new String(this.fieldPassword.getPassword()), config.getPassword())) {
            return true;
        }

        if (isModified(selectedBoardId, config.getBoardId())) {
            return true;
        }

        if (isModified(selectedSprint == null ? null : selectedSprint.getId(), config.getSprintId())) {
            return true;
        }
        return false;
    }

    private boolean isModified(String s1, String s2) {
        if (StringUtils.isEmpty(s1)) {
            s1 = "";
        }

        if (StringUtils.isEmpty(s2)) {
            s2 = "";
        }

        return !s1.equals(s2);
    }

    private boolean isModified(Integer i1, Integer i2) {
        if (i1 == null) {
            i1 = 0;
        }

        if (i2 == null) {
            i2 = 0;
        }

        return !i1.equals(i2);

    }

    public void apply() {
        config.setJiraHome(this.fieldJiraHome.getText());
        config.setUsername(this.fieldUsername.getText());
        config.setPassword(new String(this.fieldPassword.getPassword()));
        config.setBoardId(selectedBoardId);
        config.setSprintId(selectedSprint != null ? selectedSprint.getId() : null);
        config.setSprintName(selectedSprint != null ? selectedSprint.getName() : null);
    }

    public void reset() {
        this.fieldJiraHome.setText(config.getJiraHome());
        this.fieldUsername.setText(config.getUsername());
        this.fieldPassword.setText(config.getPassword());
        this.labelSprint.setText(config.getSprintName());
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public static void main(String[] args) {
        MainForm dialog = new MainForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}


