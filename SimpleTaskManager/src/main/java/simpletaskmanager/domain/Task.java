/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

/**
 *
 * @author taina
 */
public class Task {
    
    private String header; 
    private Priority prio ; 
    private String dueDate ; // pitää selvitellä miten päivän käyttäminen oikein sujuu parhaiten
    
    public Task (String title, Priority prio) {
        this.header=title;
        this.prio=prio;
    }

    public String getHeader() {
        return header;
    }

    public Priority getPrio() {
        return prio;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setPrio(Priority prio) {
        this.prio = prio;
    }
    
    
    
    
}
