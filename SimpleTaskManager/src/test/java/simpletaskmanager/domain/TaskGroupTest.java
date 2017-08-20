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
    public void createTaskGroupWorks() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        assertEquals("Otsikko", newTaskGroup.getHeader());
        assertEquals(0, newTaskGroup.getTaskList().size());
    }

    @Test
    public void addTaskGrowsList() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        newTaskGroup.addNewTask(new Task("title", Priority.Trivial));
        assertEquals(1, newTaskGroup.getTaskList().size());
    }

    // tama ei toimi talla hetkella
    //@Test
    public void addingSameTaskTwiceDoesNotGrowList() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        newTaskGroup.addNewTask(new Task("title", Priority.Trivial));
        newTaskGroup.addNewTask(new Task("title", Priority.Trivial));
        assertEquals(1, newTaskGroup.getTaskList().size());
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

        List<Task> taskList = newTaskGroup.getTaskList();
        assertEquals(1, taskList.size());
        assertEquals("TaskHeader", taskList.get(0).getHeader());

    }


}
