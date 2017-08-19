/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author taina
 */
public class TaskGroup {

    private String uniqueID;
    private String header;
    private Boolean isTemplate;
    private List<Task> taskList;

    public TaskGroup(String title) {
        this.header = title;
        this.taskList = new ArrayList<>();
        this.isTemplate = false;
        this.uniqueID = UUID.randomUUID().toString();
    }

    public TaskGroup(List<String> taskGroupContains) {
        /*
         * aineistossa on
         * 1. rivi
         ** uniqueID; header; isTemplate
         */

        String[] firstRow = taskGroupContains.get(0).split(";");
        this.uniqueID = firstRow[0];
        this.header = firstRow[1];
        this.isTemplate = Boolean.valueOf(firstRow[2]);

        this.taskList = new ArrayList<>();
         /*
         * 2. rivi .. n.
         ** rivi on TASK tietoja uniqueID;header; prio; dueDate; status;
         * *
         */
        for (int i = 1; i < taskGroupContains.size(); i++) {
            Task t = new Task(taskGroupContains.get(i).split(";"));
            this.taskList.add(t);
        }

    }

    public boolean addNewTask(Task newTask) {
        if (taskList.contains(newTask)) {
            return false;
        }
        taskList.add(newTask);
        return true;
    }

    public String getHeader() {
        return header;
    }

    public Boolean getTemplate() {
        return isTemplate;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }
}
