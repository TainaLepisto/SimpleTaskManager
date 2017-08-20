/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

/**
 * @author taina
 *
 * tata voisi jatkokehittaa niin, etta kayttaja voisi maaritella eri tilat
 *
 */
public enum WorkFlow {


    Todo(1),
    InProgress(2),
    Done(3);

    WorkFlow(int numberStat) {
        int stat = numberStat;
    }


}
