/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.statistics;

import simpletaskmanager.domain.Priority;
import simpletaskmanager.domain.Task;
import simpletaskmanager.domain.TaskGroup;
import simpletaskmanager.domain.WorkFlow;
import simpletaskmanager.logic.TaskManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Luokka tilastotietojen kasittelyyn.
 *
 * @author taina
 */
public class Stats {

    private TaskManager tm;

    /**
     * konstruktori.
     *
     * @param tm - TaskManager luokan olio, joka pitaa sisallaan tiedot ryhmista.
     */
    public Stats(TaskManager tm) {
        this.tm = tm;
    }


    /**
     * laskuri kaikkien ryhmien tietyn statuksen ja prioriteetin tehtavien maaralle.
     *
     * @param status - mika workflown kohta rajataan
     * @param prio   - mika prioriteetti rajataan
     * @return - palautetaan laskettu tehtavien lukumaara
     */
    public int countTasks(WorkFlow status, Priority prio) {
        return tm.getTaskGroups().stream()
                .map(tg ->
                        tg.getTaskList(status, prio).size())
                .reduce(0, (total, size) -> total + size);

    }

    /**
     * Lasketaan tehtavien lukumaara per status yli ryhmien.
     *
     * @return palautetaan ryhmatiedot - jokaisen statuksen tehtavien lukumaara.
     */
    public Map<WorkFlow, Integer> countTasks() {

        Map<WorkFlow, Integer> countTasks = new HashMap<>();

        int todo = tm.getTaskGroups().stream()
                .map(tg ->
                        tg.getTaskListOrderedByPrio(WorkFlow.Todo).size())
                .reduce(0, (total, size) -> total + size);
        countTasks.put(WorkFlow.Todo, todo);

        int inProgress = tm.getTaskGroups().stream()
                .map(tg ->
                        tg.getTaskListOrderedByPrio(WorkFlow.InProgress).size())
                .reduce(0, (total, size) -> total + size);
        countTasks.put(WorkFlow.InProgress, inProgress);

        int done = tm.getTaskGroups().stream()
                .map(tg ->
                        tg.getTaskListOrderedByPrio(WorkFlow.Done).size())
                .reduce(0, (total, size) -> total + size);
        countTasks.put(WorkFlow.Done, done);

        return countTasks;

    }


    /**
     * Lasketaan tehtavien lukumaara per jokainen ryhma ja status.
     *
     * @return palautetaan ryhmatiedot - jokaisen ryhman ja statuksen tehtavien lukumaara.
     */
    public Map<TaskGroup, Map<String, Integer>> countTasksByGroups() {

        Map<TaskGroup, Map<String, Integer>> countTasksByGroups = new HashMap<>();
        List<TaskGroup> taskGroupList = tm.getTaskGroups();

        for (int i = 0; i < taskGroupList.size(); i++) {
            TaskGroup currentTG = taskGroupList.get(i);
            countTasksByGroups.put(currentTG, new HashMap<>());

            countTasksByGroups.get(currentTG).put(WorkFlow.Todo.toString(), currentTG.getTaskListOrderedByPrio(WorkFlow.Todo).size());
            countTasksByGroups.get(currentTG).put(WorkFlow.InProgress.toString(), currentTG.getTaskListOrderedByPrio(WorkFlow.InProgress).size());
            countTasksByGroups.get(currentTG).put(WorkFlow.Done.toString(), currentTG.getTaskListOrderedByPrio(WorkFlow.Done).size());
        }

        return countTasksByGroups;

    }


    /**
     * Lasketaan mika on isoin yksittaisessa ryhmassa oleva tehtavien lukumaara.
     *
     * @return palautetaan suurin laskettu lukumaara.
     */
    public long getMaxTaskCountInGroup() {
        return tm.getTaskGroups().stream()
                .mapToInt(tg -> tg.getTaskList().size())
                .max().getAsInt();
    }


    /**
     * Lasketaan annetun ryhman tehtavien lukumaara jokaisessa statuksessa.
     *
     * @param newTaskGroup - kasiteltava ryhma.
     * @return - kokoelma status+tehtavien lukumaara
     */
    public Map<String, Integer> countTasksByStatus(TaskGroup newTaskGroup) {

        Map<String, Integer> countTasksByStatus = new HashMap<>();

        countTasksByStatus.put(WorkFlow.Todo.toString(), getTaskListCounterFromGroup(newTaskGroup, WorkFlow.Todo));
        countTasksByStatus.put(WorkFlow.InProgress.toString(), getTaskListCounterFromGroup(newTaskGroup, WorkFlow.InProgress));
        countTasksByStatus.put(WorkFlow.Done.toString(), getTaskListCounterFromGroup(newTaskGroup, WorkFlow.Done));

        return countTasksByStatus;

    }

    private Integer getTaskListCounterFromGroup(TaskGroup newTaskGroup, WorkFlow status) {
        List<Task> getList = newTaskGroup.getTaskListOrderedByPrio(status);
        if (getList.isEmpty()) {
            return 0;
        } else {
            return getList.size();
        }
    }


}
