/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.logic;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author taina
 */
public class FileManager {


    private List<String> rowsInFile;

    public FileManager() {
        rowsInFile = new ArrayList<>();

    }

    public boolean readFile(String fileName) {
        try {
            rowsInFile = Files.lines(Paths.get(fileName)).collect(Collectors.toCollection(ArrayList::new));
            return true;
        } catch (IOException ex) {
            return false;
            //throw new RuntimeException("Tiedoston " + fileName + " lukeminen epäonnistui. Virhe: " + ex.getMessage());
        }
    }

    public List<String> getRows() {
        return rowsInFile;
    }

}

    

