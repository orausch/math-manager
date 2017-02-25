import com.alee.laf.WebLookAndFeel;
import form.Start;

import javax.swing.*;
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
