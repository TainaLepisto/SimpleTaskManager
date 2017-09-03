/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

/**
 * Enum tieto tehtavien tarkeysasteesta.
 *
 * @author taina
 * '
 */
public enum Priority {

    Blocker(3),
    Critical(2),
    Major(1),
    Neutral(0),
    Minor(-1),
    Trivial(-2);

    private int prio;

    Priority(int numberPrio) {
        this.prio = numberPrio;
    }

    public int getArvo() {
        return this.prio;
    }
}
