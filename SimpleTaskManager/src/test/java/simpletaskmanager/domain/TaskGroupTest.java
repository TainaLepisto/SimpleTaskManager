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
import static org.junit.Assert.*;

/**
 *
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
    
    @Test
    public void addingSameTaskTwiceDoesNotGrowList() {
        TaskGroup newTaskGroup = new TaskGroup("Otsikko");
        newTaskGroup.addNewTask(new Task("title", Priority.Trivial));
        newTaskGroup.addNewTask(new Task("title", Priority.Trivial));
        assertEquals(1, newTaskGroup.getTaskList().size());
    }
}
