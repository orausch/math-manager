import com.alee.laf.WebLookAndFeel;
import form.Start;

import javax.swing.*;

/**
 * Main method, sets the UI look and field for the whole program and shows the Start form
 */
public class StartupManager {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WebLookAndFeel());
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        Start.show();

    }


}
