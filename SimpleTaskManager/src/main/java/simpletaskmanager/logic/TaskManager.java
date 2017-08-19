/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.logic;

import java.util.ArrayList;
import java.util.List;

import simpletaskmanager.domain.*;

/**
 * @author taina
 */
public class TaskManager {

    private List<TaskGroup> taskGroupLists;
    FileManager fm;

    public TaskManager() {
        this.taskGroupLists = new ArrayList<>();
        this.fm = new FileManager();
    }

    public void readFile() {
        // tässä tiedostossa on ryhmien ID tunnukset - jokainen omalla rivillä
        fm.readFile("/files/SimpleTaskManager.txt");
        List<String> allTaskGroups = fm.getRows();

        // luodaan jokaisesta ryhmästä oma olio
        for (String allTaskGroup : allTaskGroups) {
            // jokainen ryhmä on omassa tiedostossa
            System.out.println();
            fm.readFile("../../files/" + allTaskGroup + ".txt");
            List<String> taskGroupContains = fm.getRows();
            TaskGroup tg = new TaskGroup(taskGroupContains);
            this.taskGroupLists.add(tg);
        }

    }

    public TaskGroup cloneTaskGroup(String newTitle, TaskGroup oldTaskGroup) {
        TaskGroup newTaskGroup = new TaskGroup(newTitle);

        List<Task> taskList = oldTaskGroup.getTaskList();

        for (Task aTaskList : taskList) {
            newTaskGroup.addNewTask(aTaskList.cloneTask());
        }

        this.taskGroupLists.add((newTaskGroup));
        return newTaskGroup;
    }

    public List<TaskGroup> getTaskGroups() {
        return this.taskGroupLists;
    }

}
