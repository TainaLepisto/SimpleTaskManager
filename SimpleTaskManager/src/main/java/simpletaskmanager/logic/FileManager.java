/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.logic;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Luokka tiedostojen kasittelyyn. Kaynnistaessa sovellusta luetaan tiedot.
 * Suljettaessa taas kirjoitetaan ne muistiin. (todellisuudessa pitaisi varmasti kirjoittaa usemminkin,
 * mutta nyt mennaan nain, jotta tasta ei tule vain tiedoston kasittelya.).
 *
 * @author taina
 */
public class FileManager {


    private List<String> rowsInFile;

    /**
     * konstruktori.
     */
    public FileManager() {
        this.rowsInFile = new ArrayList<>();

    }

    /**
     * tietojen lukeminen tiedostosta.
     *
     * @param fileName luettava tiedosto.
     * @return boolean lukemisen onnistumisesta.
     */
    public boolean readFile(String fileName) {
        try {
            this.rowsInFile = Files.lines(Paths.get(fileName)).collect(Collectors.toCollection(ArrayList::new));
            return true;
        } catch (IOException ex) {
            System.out.println("tiedostoa ei loytynyt");
            return false;
            //throw new RuntimeException("Tiedoston " + fileName + " lukeminen epaonnistui. Virhe: " + ex.getMessage());
        }
    }

    public List<String> getRows() {
        return this.rowsInFile;
    }


}

    

