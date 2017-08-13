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

    private List<TaskGroup> taskGroupLists;
    FileManager fm = new FileManager();

    public TaskManager() {
        // tässä tiedostossa on ryhmien ID tunnukset - jokainen omalla rivillä
        fm.readFile("file:resources/files/SimpleTaskManager.txt");
        List<String> allTaskGroups = fm.getRows();

        // luodaan jokaisesta ryhmästä oma olio
        for (int i = 0; i < allTaskGroups.size(); i++) {
            // jokainen ryhmä on omassa tiedostossa
            fm.readFile("file:resources/files/" + allTaskGroups.get(i) + ".txt");
            List<String> taskGroupContains = fm.getRows();
            TaskGroup tg = new TaskGroup(taskGroupContains);
            taskGroupLists.add(tg);
        }

    }

    public TaskGroup cloneTaskGroup(String newTitle, TaskGroup oldTaskGroup) {
        TaskGroup newTaskGroup = new TaskGroup(newTitle);

        List<Task> taskList = oldTaskGroup.getTaskList();

        for (int i = 0; i < taskList.size(); i++) {
            newTaskGroup.addNewTask(taskList.get(i).cloneTask());
        }

        return newTaskGroup;
    }

    public List<TaskGroup> getTaskGroups() {
        return this.taskGroupLists;
    }

}
