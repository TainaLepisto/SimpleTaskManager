/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.logic;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import simpletaskmanager.domain.*;

/**
 * @author taina
 */
public class TaskManagerTest {

    public TaskManagerTest() {
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
    public void cloneEmpthyTaskGroupWorks() {
        TaskManager tm = new TaskManager();
        TaskGroup tg = new TaskGroup("title");
        TaskGroup tg2 = tm.cloneTaskGroup("new title", tg);
        assertEquals(0, tg2.getTaskList().size());
        assertEquals("new title", tg2.getHeader());
    }

    @Test
    public void cloneTaskGroupWithTasksCopieasAllTasks() {
        TaskManager tm = new TaskManager();
        TaskGroup tg = new TaskGroup("title");
        tg.addNewTask(new Task("title1", Priority.Trivial));
        tg.addNewTask(new Task("title2", Priority.Trivial));
        tg.addNewTask(new Task("title3", Priority.Trivial));
        // kopioidaan ryhma uudeksi ryhmaksi
        TaskGroup tg2 = tm.cloneTaskGroup("new title", tg);
        // tarkastetaan, etta kaikki tehtavat kopioituivat
        assertEquals(3, tg2.getTaskList().size());
    }

    @Test
    public void cloneTaskGroupWithTasksSetsAllTasksToTODO() {
        TaskManager tm = new TaskManager();
        TaskGroup tg = new TaskGroup("title");
        // luodaan ryhmalle kolme tehtavaa, joista osa merkitaan eri tilaan kuin TODO
        Task task1 = new Task("Otsikko", Priority.Major);
        tg.addNewTask(task1);
        task1.setStatus(WorkFlow.InProgress);
        Task task2 = new Task("Otsikko", Priority.Major);
        task2.setStatus(WorkFlow.InProgress);
        tg.addNewTask(task2);
        tg.addNewTask(new Task("title", Priority.Trivial));
        // kopioidaan ryhma uudeksi ryhmaksi
        TaskGroup tg2 = tm.cloneTaskGroup("new title", tg);
        // muutetaan alkuperaisen tehtavan tilaa
        task1.setStatus(WorkFlow.Done);
        // kaikki uuden ryhman tehtavat pitaa olla TODO tilassa
        List<Task> taskList = tg2.getTaskList();
        for (Task aTaskList : taskList) {
            assertEquals(WorkFlow.Todo, aTaskList.getStatus());
        }
    }


    @Test
    public void readGroupInfoFromFile() {
        TaskManager tm = new TaskManager();
        tm.readFile();
        List<TaskGroup> taskGroupList = tm.getTaskGroups();
        assertEquals(2, taskGroupList.size());
    }



}
