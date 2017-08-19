/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

/**
 * @author taina
 * <p>
 * <p>
 * tätä voisi jatkokehittää niin, että käyttäjä voisi määritellä eri tilat
 * -> jätetään seuraavaan vaiheeseen
 */
public enum WorkFlow {


    Todo(1),
    InProgress(2),
    Done(3);

    WorkFlow(int numberStat) {
        int stat = numberStat;
    }


}
