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
public enum Priority {
 
    Blocker("3"),
    Critical("2"),
    Major("1"),
    Neutral("0"),
    Minor("-1"),
    Trivial("-2");

    private String koodi;        

    Priority(String koodi) {
        this.koodi = koodi;
    }
    
    public int getArvo() {
        // ei tarvi try lohkoa, koska numerot on tuolla ylhäällä meidän kovakoodaamat
        return Integer.parseInt(this.koodi);
    }
    
    
    
}
