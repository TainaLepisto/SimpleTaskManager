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
        this.rowsInFile = new ArrayList<>();

    }

    public boolean readFile(String fileName) {
        try {
            this.rowsInFile = Files.lines(Paths.get(fileName)).collect(Collectors.toCollection(ArrayList::new));
            return true;
        } catch (IOException ex) {
            System.out.println("tiedostoa ei löytynyt");
            return false;
            //throw new RuntimeException("Tiedoston " + fileName + " lukeminen epäonnistui. Virhe: " + ex.getMessage());
        }
    }

    public List<String> getRows() {
        return this.rowsInFile;
    }

}

    

