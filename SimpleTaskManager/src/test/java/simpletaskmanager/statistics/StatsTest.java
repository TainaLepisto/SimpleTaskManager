package simpletaskmanager.statistics;

import org.junit.Before;
import org.junit.Test;
import simpletaskmanager.domain.Priority;
import simpletaskmanager.domain.Task;
import simpletaskmanager.domain.TaskGroup;
import simpletaskmanager.domain.WorkFlow;
import simpletaskmanager.logic.TaskManager;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StatsTest {

    private Stats st ;
    private TaskGroup tg ;

    @Before
    public void setUp() {

        TaskManager tm = new TaskManager();
        tg = new TaskGroup("title");
        tg.addNewTask(new Task("title1", Priority.Trivial));
        tg.addNewTask(new Task("title2", Priority.Major));
        TaskGroup tg2 = new TaskGroup("title2");
        tg2.addNewTask(new Task("title3", Priority.Major));
        tm.addNewTaskGroup(tg);
        tm.addNewTaskGroup(tg2);

        this.st = new Stats(tm);

    }

    @Test
    public void countTasksByStatusAndPrioWorks() {
        assertEquals(0, st.countTasks(WorkFlow.InProgress, Priority.Major));
        assertEquals(1, st.countTasks(WorkFlow.Todo, Priority.Trivial));
        assertEquals(2, st.countTasks(WorkFlow.Todo, Priority.Major));
    }


    @Test
    public void countTasksWorks() {
        Map<WorkFlow, Integer> countTasks  = st.countTasks();
        assertEquals(3, countTasks.size());
        assertEquals(3, countTasks.get(WorkFlow.Todo).intValue()) ;
        assertEquals(0, countTasks.get(WorkFlow.Done).intValue()) ;

    }

    @Test
    public void getMaxTaskCountInGroupWorks() {
        assertEquals(2,  st.getMaxTaskCountInGroup());
    }


    @Test
    public void countTasksByGroupsWorks() {
        Map<TaskGroup, Map<String, Integer>> countTasksByGroups  = st.countTasksByGroups();
        assertEquals(2, countTasksByGroups.size());
        assertEquals(0, countTasksByGroups.get(tg).get(WorkFlow.Done.toString()).intValue()) ;
        assertEquals(2, countTasksByGroups.get(tg).get(WorkFlow.Todo.toString()).intValue()) ;

    }

    @Test
    public void countTasksByStatusWorks() {
        Map<String, Integer> countTasksByStatus  = st.countTasksByStatus(tg);
        assertEquals(3, countTasksByStatus.size());
        assertEquals(0, countTasksByStatus.get(WorkFlow.Done.toString()).intValue()) ;
        assertEquals(2, countTasksByStatus.get(WorkFlow.Todo.toString()).intValue()) ;

    }

}
