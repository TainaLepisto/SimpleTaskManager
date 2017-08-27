package simpletaskmanager;

import simpletaskmanager.userinterface.Desktop;

import static javafx.application.Application.launch;


/**
 * Luokka kayttoliittyman kaynnistamiselle.
 *
 * @author taina
 */
public class StartProgram {

    /**
     * kaynnistaa ohjelman.
     *
     * @param args oletustiedot.
     */
    public static void main(String[] args) {
        launch(Desktop.class);
    }


}
