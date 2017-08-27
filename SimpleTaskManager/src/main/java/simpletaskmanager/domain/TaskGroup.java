/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Luokka sisaltaa tehtavaryhman.
 * Ryhma on hallittava kokonaisuus.
 * Ryhma sisaltaa tehtavia (Task).
 *
 * @author taina
 */
public class TaskGroup {

    private String uniqueID;
    private String header;
    private Boolean isTemplate;
    private String iconName;
    private List<Task> taskList;
    private String desc;

    /**
     * Konstruktori uuden luomiseen.
     *
     * @param title ryhman otsikko
     */
    public TaskGroup(String title) {
        this.header = title;
        this.taskList = new ArrayList<>();
        this.isTemplate = false;
        this.iconName = "default";
        this.uniqueID = UUID.randomUUID().toString();
        this.desc = "lorem ipsun.. ";
    }

    /**
     * Konstruktori kun tiedot luetaan tallesta.
     *
     * @param taskGroupContains tallesta luettut tiedot
     *                          * aineistossa on
     *                          1. rivi ryhman tiedot
     *                          * uniqueID; header; isTemplate; iconName; desc
     *                          <p>
     *                          * 2. rivi .. n. ryhmaan kuuluvien tehtavien tiedot
     *                          uniqueID;header; prio; dueDate; status;
     */
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
        this.iconName = firstRow[3];
        this.desc = firstRow[4];

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

    /**
     * Konstruktori uuden luomiseen.
     *
     * @param header     tehtavan otsikko
     * @param isTemplate tehtavan tarkeys
     * @param iconName   ryhman iconin nimi
     * @param desc       ryhman kuvaus
     */
    public TaskGroup(String header, boolean isTemplate, String iconName, String desc) {
        this.header = header;
        this.taskList = new ArrayList<>();
        this.isTemplate = isTemplate;
        this.iconName = iconName;
        this.uniqueID = UUID.randomUUID().toString();
        this.desc = desc;
    }

    /**
     * Metodilla listaan ryhmaan uusi taski.
     *
     * @param newTask tehtava, joka lisataan listalle
     * @return boolean arvo onnistumisesta
     */
    public boolean addNewTask(Task newTask) {
        if (taskList.contains(newTask)) {
            return false;
        }
        taskList.add(newTask);
        return true;
    }

    /**
     * Metodi, jolla haetaan ryhman tietyssa tilassa olevat tehtavat.
     *
     * @param status minka tilan tehtavat haetaan
     * @return lista tehtavista
     */
    public List<Task> getTaskListByStatus(WorkFlow status) {
        if (this.taskList == null) {
            return new ArrayList<>();
        }
        return this.taskList.stream()
                .filter(task -> task.getStatus() == status)
                //.sorted()
                .collect(Collectors.toCollection(ArrayList::new));
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

    public String getIconName() {
        return iconName;
    }

    public String getDesc() {
        return desc;
    }
}
