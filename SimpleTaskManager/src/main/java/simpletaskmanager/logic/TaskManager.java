/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.logic;

import java.util.List;
import simpletaskmanager.domain.*;

/**
 *
 * @author taina
 */
public class TaskManager {
    
    public TaskGroup cloneTaskGroup(String newTitle, TaskGroup oldTaskGroup) {
        TaskGroup newTaskGroup = new TaskGroup(newTitle);
        
        List<Task> taskList = oldTaskGroup.getTaskList();
        
        for (int i = 0; i < taskList.size(); i++) {
            newTaskGroup.addNewTask(taskList.get(i).cloneTask());
        }
        
        return newTaskGroup;
    }
    
    
}
