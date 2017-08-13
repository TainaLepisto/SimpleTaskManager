/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

import java.util.Objects;

/**
 *
 * @author taina
 */
public class Task {

    private String header;
    private Priority prio;
    private String dueDate; // pitää selvitellä miten päivän käyttäminen oikein sujuu parhaiten
    private WorkFlow status;

    public Task(String title, Priority prio) {
        this.header = title;
        this.prio = prio;
        this.status = WorkFlow.Todo;
    }

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
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.header);
        hash = 37 * hash + Objects.hashCode(this.prio);
        hash = 37 * hash + Objects.hashCode(this.dueDate);
        hash = 37 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (!Objects.equals(this.header, other.header)) {
            return false;
        }
        if (!Objects.equals(this.dueDate, other.dueDate)) {
            return false;
        }
        if (this.prio != other.prio) {
            return false;
        }
        return this.status == other.status;
    }

}
