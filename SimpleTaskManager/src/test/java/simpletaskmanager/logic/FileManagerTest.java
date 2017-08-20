package simpletaskmanager.logic;

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

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author taina
 */
public class FileManagerTest {
    
    public FileManagerTest() {
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
    public void readingFileIsSuccess() {
        FileManager fm = new FileManager();
        assertTrue(fm.readFile(TaskManager.class.getResource("../../files/SimpleTaskManager.txt").getPath()));
    }

    @Test
    public void readingFileThatDoesNotExist() {
        FileManager fm = new FileManager();
        assertFalse(fm.readFile("../../files/DoesNotExist.txt"));
    }

}
