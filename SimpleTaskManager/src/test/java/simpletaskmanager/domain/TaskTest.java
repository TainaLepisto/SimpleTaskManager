package simpletaskmanager.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class TaskTest {
    
    public TaskTest() {
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
    public void createNewTaskWorks() {
        Task newTask = new Task("Otsikko", Priority.Major);
        assertEquals("Otsikko", newTask.getHeader());
        assertEquals(Priority.Major, newTask.getPrio());
        assertEquals(WorkFlow.Todo, newTask.getStatus()); 
    }

    
    @Test
    public void statusChangeWorks() {
        Task task1 = new Task("Otsikko", Priority.Major);
        task1.setStatus(WorkFlow.Done);
        assertEquals(WorkFlow.Done, task1.getStatus());
    }
    
    @Test
    public void cloneWorks() {
        Task task1 = new Task("Otsikko", Priority.Major);
        task1.setStatus(WorkFlow.Done);
        Task task2 = task1.cloneTask();
        assertEquals(WorkFlow.Todo, task2.getStatus());
    }
    
    
}
