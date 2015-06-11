import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.janote.controller.MainController;
import com.janote.model.entities.Teacher;
import com.janote.view.console.QuickTests;
import com.janote.view.gui.MainWindow;

/**
 * Janote class Contains the main method.
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class Janote {

    /**
     * Main function of the application.
     * 
     * @param args
     */

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        boolean gui = true;

        if (gui) {
            try {
                // UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                // UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                // UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                // UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");

                for (LookAndFeelInfo info : UIManager
                        .getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            }
            catch (Exception e) {
                // If Nimbus is not available, you can set the GUI to another
                // look and feel.
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            }

            // Create the model
            Teacher t = new Teacher();
            // Create the controler
            MainController controller = new MainController(t);
            // Create the main window and link it to the controller
            MainWindow window = new MainWindow(controller);
            t.addObserver(window);

        }
        else {
            QuickTests qt = new QuickTests(); // no need to create an instance
                                              // that will not be used and will
                                              // issue a WARNING
            qt.run();
        }
    }
}
