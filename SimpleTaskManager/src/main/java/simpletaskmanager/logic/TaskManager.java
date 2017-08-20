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
 *
 * Talla hallitaan ryhmia. Kayttoliittyma kayttaa tata luokkaa.
 * Kaikki logiikka ryhmien kasittelyyn tulee tanne.
 *
 * @author taina
 *
 */
public class TaskManager {

    private List<TaskGroup> taskGroupLists;
    FileManager fm;

    public TaskManager() {
        this.taskGroupLists = new ArrayList<>();
        this.fm = new FileManager();
    }

    public void readFile() {
        // tassa tiedostossa on ryhmien ID tunnukset - jokainen omalla rivilla
        fm.readFile(TaskManager.class.getResource("../../files/SimpleTaskManager.txt").getPath());
        List<String> allTaskGroups = fm.getRows();

        // luodaan jokaisesta ryhmasta oma olio
        for (String allTaskGroup : allTaskGroups) {
            // jokainen ryhma on omassa tiedostossa
            try {
                //System.out.println("../../files/" + allTaskGroup + ".txt");
                fm.readFile(TaskManager.class.getResource("../../files/" + allTaskGroup + ".txt").getPath());
                List<String> taskGroupContains = fm.getRows();
                TaskGroup tg = new TaskGroup(taskGroupContains);
                this.taskGroupLists.add(tg);
            } catch (Exception e) {
            }
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
