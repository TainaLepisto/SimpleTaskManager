/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import java.util.Objects;
import java.util.UUID;


/**
 * Luokka sisaltaa yksittaisen tehtavan tiedot.
 *
 * @author taina
 */
public class Task {

    private String uniqueID;
    private String header;
    private Priority prio;
    private String dueDate; // pitaa selvitella miten paivan kayttaminen oikein sujuu parhaiten
    private WorkFlow status;

    /**
     * Konstruktori uuden luomiseen.
     *
     * @param title tehtavan otsikko
     * @param prio  tehtavan tarkeys
     */
    public Task(String title, Priority prio) {
        this.header = title;
        this.prio = prio;
        this.status = WorkFlow.Todo;
        uniqueID = UUID.randomUUID().toString();
    }

    /**
     * Konstruktori kun tiedot luetaan tallesta.
     *
     * @param rowInfo taulukko tehtavan tiedoista
     *                0=ID
     *                1=otsikko
     *                2=priority /tarkeys
     *                3=deadline
     *                4=status/workflow state
     */
    public Task(String[] rowInfo) {
        this.uniqueID = rowInfo[0];
        this.header = rowInfo[1];
        this.prio = Priority.valueOf(rowInfo[2]);
        this.dueDate = rowInfo[3];
        this.status = WorkFlow.valueOf(rowInfo[4]);
    }


    /**
     * Metodi luo uuden taskin olemassa olevan taskin tiedoilla.
     * taski palaa TODO tilaan ja saa uuden IDn.
     *
     * @return uusi Task olio
     */
    public Task cloneTask() {
        return new Task(this.header, this.prio);
    }

    /* getterit ja setterit */
    public String getDueDate() {
        return dueDate;
    }

    public WorkFlow getStatus() {
        return status;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(WorkFlow status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        return uniqueID.equals(task.uniqueID);
    }

    @Override
    public int hashCode() {
        return uniqueID.hashCode();
    }
}
