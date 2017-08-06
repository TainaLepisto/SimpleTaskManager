/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import simpletaskmanager.domain.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author taina
 */
public class TaskGroup {
    
    private List<Task> taskList;
    
    public TaskGroup() {
        taskList = new ArrayList<>();
    }
    
    public boolean addNewTask(Task newTask) {
        // TODO: tarkastus, ettei samanlaista jo ole
        taskList.add(newTask);
        return true;
    }
    
    
    
    
    
    
}
