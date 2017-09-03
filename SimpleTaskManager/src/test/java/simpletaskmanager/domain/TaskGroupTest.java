/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author taina
 */
public class TaskGroupTest {

    public TaskGroupTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void createTaskGroupDefaultWorks() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        assertEquals("Otsikko", newTaskGroup.getHeader());
        assertEquals(0, newTaskGroup.getTaskList().size());
    }

    @Test
    public void createTaskGroupWorks() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko", false, "hands", "kuvaus");
        assertEquals("Otsikko", newTaskGroup.getHeader());
        assertEquals("hands", newTaskGroup.getIconName());
        assertEquals(0, newTaskGroup.getTaskList().size());
    }

    @Test
    public void addTaskGrowsList() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        newTaskGroup.addNewTask(new Task("title", Priority.Trivial));
        assertEquals(1, newTaskGroup.getTaskList().size());
    }

    @Test
    public void addingSameTaskTwiceDoesNotGrowList() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        Task newTask = new Task("title", Priority.Trivial);
        assertTrue(newTaskGroup.addNewTask(newTask));
        assertFalse(newTaskGroup.addNewTask(newTask));
        assertEquals(1, newTaskGroup.getTaskList().size());
    }

    @Test
    public void getTaskListByStatusWorks() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        assertEquals(0, newTaskGroup.getTaskListOrderedByPrio(WorkFlow.Todo).size());

        Task newTask = new Task("title", Priority.Trivial);
        newTaskGroup.addNewTask(newTask);
        assertEquals(1, newTaskGroup.getTaskListOrderedByPrio(WorkFlow.Todo).size());
        assertEquals(0, newTaskGroup.getTaskListOrderedByPrio(WorkFlow.InProgress).size());

        Task newTask2 = new Task("title", Priority.Major);
        newTaskGroup.addNewTask(newTask2);
        assertEquals(newTask2, newTaskGroup.getTaskListOrderedByPrio(WorkFlow.Todo).get(0));

    }

    @Test
    public void getTaskListByStatusAndPrioWorks() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        assertEquals(0, newTaskGroup.getTaskListOrderedByPrio(WorkFlow.Todo).size());

        Task newTask = new Task("title", Priority.Trivial);
        newTaskGroup.addNewTask(newTask);
        Task newTask2 = new Task("title", Priority.Major);
        newTaskGroup.addNewTask(newTask2);
        assertEquals(1, newTaskGroup.getTaskList(WorkFlow.Todo, Priority.Major).size());
        assertEquals(0, newTaskGroup.getTaskList(WorkFlow.InProgress, Priority.Major).size());

    }

    // Gettereita ja settereita ei tarvitse testata, jos ne eivat tee mitaan monimutkaista
    // mutta jos halutaan rivikattavuus hyvaksi, niin ne on pakko testata
    @Test
    public void testAllGettersAndSetters() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");

        newTaskGroup.setHeader("Toinen");
        assertEquals("Toinen", newTaskGroup.getHeader());

        newTaskGroup.setTemplate(true);
        assertTrue(newTaskGroup.getTemplate());

        assertEquals("default", newTaskGroup.getIconName());

        assertEquals("lorem ipsun.. ", newTaskGroup.getDesc());

        assertEquals(0, newTaskGroup.getTaskList().size());
        Task newTask = new Task("title", Priority.Trivial);
        List<Task> taskList = new ArrayList<>();
        taskList.add(newTask);
        newTaskGroup.setTaskList(taskList);
        assertEquals(1, newTaskGroup.getTaskList().size());

    }

    @Test
    public void taskGroupCreatedFromFileInfo() {

        List<String> taskGroupInfo = new ArrayList<>();
        taskGroupInfo.add("1;Header;true;home;Kuvaus");
        taskGroupInfo.add("2;TaskHeader;Minor;12122012;Done");

        TaskGroup newTaskGroup = new TaskGroup(taskGroupInfo);

        assertEquals("Header", newTaskGroup.getHeader());
        assertTrue(newTaskGroup.getTemplate());
        assertEquals("home", newTaskGroup.getIconName());
        assertEquals("Kuvaus", newTaskGroup.getDesc());
        assertEquals("1", newTaskGroup.getId());


        List<Task> taskList = newTaskGroup.getTaskList();
        assertEquals(1, taskList.size());
        assertEquals("TaskHeader", taskList.get(0).getHeader());

    }


}
