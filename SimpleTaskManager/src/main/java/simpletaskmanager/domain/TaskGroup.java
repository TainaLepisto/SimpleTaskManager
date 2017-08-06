/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author taina
 */
public class TaskGroup {
    
    private String header;
    private List<Task> taskList;
    
    public TaskGroup(String title) {
        this.header = title;
        taskList = new ArrayList<>();
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
    
    

    public List<Task> getTaskList() {
        return taskList;
    }
    
    
    
    
}
