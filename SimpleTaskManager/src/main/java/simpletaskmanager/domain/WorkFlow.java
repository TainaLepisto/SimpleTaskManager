/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.domain;

/**
 *
 * Enum tieto tehtavien tiloille (Todo, InProgress,Done)
 * Jatkokehityksena mahdollisesti naiden hallinnointi oman luokan kautta, jotta
 * kayttaja voisi itse maaritella tilat.
 *
 * @author taina
 *
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
