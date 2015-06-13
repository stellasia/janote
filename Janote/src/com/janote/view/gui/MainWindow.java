package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.janote.controller.MainController;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;
import com.janote.observer.Observable;
import com.janote.observer.Observer;
import com.janote.view.gui.table.ExamTab;
import com.janote.view.gui.table.GroupTab;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements Observer {

    final int TAB_WELCOME = 0;
    final int TAB_GROUP = 1;
    final int TAB_EXAMS = 2;
    final int TAB_HELP = 3;

    private final MainController cont;
    private final JTabbedPane tabs;

    private final WelcomeTab welcomeTab;
    private final GroupTab groupTab;
    private final ExamTab examTab;
    private final HelpTab helpTab;

    private final JMenuBar menuBar = new JMenuBar();

    // protected String statusMessage = "Prêt";
    protected JLabel statusLabel;

    public MainWindow(MainController pCont) {

        /*
         * // Retrieve the user preference node for the package com.mycompany
         * Preferences prefs =
         * Preferences.userNodeForPackage(com.mycompany.MyClass.class);
         * 
         * // Preference key name final String PREF_NAME = "name_of_preference";
         * 
         * // Set the value of the preference String newValue = "a string";
         * prefs.put(PREF_NAME, newValue);
         * 
         * // Get the value of the preference; // default value is returned if
         * the preference does not exist String defaultValue = "default string";
         * String propertyValue = prefs.get(PREF_NAME, defaultValue); //
         * "a string"
         */

        this.cont = pCont;
        // general property of the main window
        this.setTitle("JaNote, gestionnaire de notes");
        this.setSize(900, 600);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(900, 600));
        ImageIcon img = new ImageIcon(getClass().getResource(
                "/icon-notebook.png")); // application icon displayed in the
                                        // Unity dock for example
        this.setIconImage(img.getImage());

        this.tabs = new JTabbedPane(JTabbedPane.LEFT);

        // ====================================
        // Welcome tab
        // JPanel pan = new JPanel();
        welcomeTab = new WelcomeTab(this);
        JLabel lbl = new JLabel("Accueil");
        Icon icon = new ImageIcon(getClass().getResource("/icon-notebook.png"));
        lbl.setIcon(icon);
        lbl.setIconTextGap(3);
        lbl.setVerticalTextPosition(SwingConstants.TOP);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(20.0f));

        tabs.add("Pan1", welcomeTab);
        tabs.setTabComponentAt(TAB_WELCOME, lbl);
        tabs.setToolTipTextAt(TAB_WELCOME, "Accueil");// Changing the tooltip
                                                      // for first tab

        // ====================================
        // Group tab
        JLabel lbl2 = new JLabel("Groupes");
        Icon icon2 = new ImageIcon(getClass().getResource("/icon-group.png"));
        lbl2.setIcon(icon2);
        lbl2.setIconTextGap(3);
        lbl2.setVerticalTextPosition(SwingConstants.TOP);
        lbl2.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl2.setFont(lbl.getFont().deriveFont(20.0f));

        groupTab = new GroupTab(cont.getGroupColTitlesView(), this);
        tabs.add("Pan2", groupTab);
        tabs.setTabComponentAt(TAB_GROUP, lbl2);
        tabs.setToolTipTextAt(TAB_GROUP, "Liste des étudiants");// Changing the
                                                                // tooltip for
                                                                // second tab

        // ====================================
        // Exams tab
        JLabel lbl3 = new JLabel("Notes");
        Icon icon3 = new ImageIcon(getClass().getResource("/icon-exam.png"));
        lbl3.setIcon(icon3);
        lbl3.setIconTextGap(3);
        lbl3.setVerticalTextPosition(SwingConstants.TOP);
        lbl3.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl3.setFont(lbl.getFont().deriveFont(20.0f));

        examTab = new ExamTab(cont.getExamColTitlesView(), this);
        tabs.add("Pan3", examTab);
        tabs.setTabComponentAt(TAB_EXAMS, lbl3);
        tabs.setToolTipTextAt(TAB_EXAMS, "Gérer les notes");// Changing the
                                                            // tooltip for first
                                                            // tab

        // ====================================
        // Help tab
        JLabel lbl4 = new JLabel("Aide");
        Icon icon4 = new ImageIcon(getClass().getResource("/icon-help.png"));
        // Icon icon4 = new
        // ImageIcon(getClass().getResource("images/icon-help.png"));
        lbl4.setIcon(icon4);
        lbl4.setIconTextGap(3);
        lbl4.setVerticalTextPosition(SwingConstants.TOP);
        lbl4.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl4.setFont(lbl.getFont().deriveFont(20.0f));

        helpTab = new HelpTab();
        // pan4.add(help);

        tabs.add("Pan4", helpTab);
        tabs.setTabComponentAt(TAB_HELP, lbl4);
        tabs.setToolTipTextAt(TAB_HELP, "Je ne comprends rien ?");// Changing
                                                                  // the
                                                                  // tooltip
                                                                  // for first
                                                                  // tab

        // Working, to implement correctly after the test phase
        tabs.setEnabledAt(TAB_GROUP, false);
        tabs.setEnabledAt(TAB_EXAMS, false);

        // create the status bar panel and shove it down the bottom of the frame
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel("En attende d'un fichier de données... ");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        // ====================================
        // On window close, save user preferences before exiting
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // System.out.println("Closed");

                // Retrieve the user preference node for the package
                // com.mycompany
                Preferences prefs = Preferences.userNodeForPackage(this
                        .getClass());
                // Preference key name
                final String PREF_NAME = WelcomeTab.prefNameLastSqlFile;
                // Set the value of the preference
                String newValue = welcomeTab.getFileName();
                if (newValue != null)
                    prefs.put(PREF_NAME, newValue);

                e.getWindow().dispose();
            }
        });

        // ====================================
        // init the menus
        this.initMenus();
        this.setJMenuBar(menuBar);

        // ====================================
        // Show the window
        this.getContentPane().add(tabs, BorderLayout.CENTER);
        this.setVisible(true);

    }

    public void initMenus() {
        // File menu
        JMenu file = new JMenu("Fichier");
        file.setMnemonic('F');
        JMenuItem newFile = new JMenuItem("Nouveau fichier");
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                KeyEvent.CTRL_MASK));
        newFile.addActionListener(welcomeTab.new NewFile());
        JMenuItem openFile = new JMenuItem("Ouvrir un fichier");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                KeyEvent.CTRL_MASK));
        openFile.addActionListener(welcomeTab.new OpenFile());
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                KeyEvent.CTRL_MASK));

        /*
         * JMenu save = new JMenu("Sauvegarder"); JMenuItem saveAll = new
         * JMenuItem("Toutes les modifications"); save.add(saveAll); JMenuItem
         * saveSelGroup = new JMenuItem("Le groupe actif");
         * save.add(saveSelGroup); JMenuItem saveSelStudents = new
         * JMenuItem("Les étudiants sélectionnés"); save.add(saveSelStudents);
         */

        file.add(newFile);
        file.add(openFile);
        file.addSeparator();
        // file.add(save);
        // file.addSeparator();
        file.add(quitter);

        this.menuBar.add(file);

        JMenu edit = new JMenu("Edition");
        edit.setMnemonic('E');
        JMenu newObject = new JMenu("Nouveau");
        JMenuItem newGroup = new JMenuItem("Groupe");
        newObject.add(newGroup);
        JMenuItem newStudent = new JMenuItem("Etudiant");
        newObject.add(newStudent);
        JMenuItem newExam = new JMenuItem("Examen");
        newObject.add(newExam);
        edit.add(newObject);
        JMenu delObject = new JMenu("Supprimer");
        JMenuItem delGroup = new JMenuItem("Groupe");
        delObject.add(delGroup);
        JMenuItem delStudent = new JMenuItem("Etudiant");
        delObject.add(delStudent);
        JMenuItem delExam = new JMenuItem("Examen");
        delObject.add(delExam);
        edit.add(delObject);
        this.menuBar.add(edit);

        JMenu tools = new JMenu("Outils");
        JMenuItem settings = new JMenuItem("Préférences");
        tools.add(settings);
        this.menuBar.add(tools);

        // About menu
        JMenu about = new JMenu(" ? ");
        about.setMnemonic('A');

        JMenuItem helpTab = new JMenuItem("Aide");
        helpTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                KeyEvent.CTRL_MASK));
        helpTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tabs.setSelectedIndex(TAB_HELP);
            }
        });

        JMenuItem aboutWin = new JMenuItem("A propos");
        about.add(helpTab);
        about.add(aboutWin);
        this.menuBar.add(about);

    }

    public void unlockTabs() {
        // System.out.println("MainWindow.unlockTabs");
        tabs.setEnabledAt(1, true);
        tabs.setEnabledAt(2, true);
        groupTab.init();
        examTab.init();
        setStatusMessage("Prêt.");
        // groupSelector.setItems(cont.getGroupList(), 0);
    }

    public void setStatusMessage(String mess) {
        this.statusLabel.setText(mess);
    }

    public void setDBName(String name) {
        // System.out.println("MainWindow.setDBName");
        try {
            this.cont.start(name);
        }
        catch (SQLException e) {
            System.out.println("Error starting sqlite");
        }
    }

    public MainController getController() {
        return cont;
    }

    public GroupTab getGroupTab() {
        return this.groupTab;
    }

    public void changeSelectedGroup(Group g) {
        if (this.cont.changeSelectedGroup(g)) {
            ArrayList<Student> students = this.cont.getStudentList();
            this.groupTab.updateStudentList(this.cont.getSelectedGroup(),
                    students);
            this.examTab.updateStudentList(this.cont.getSelectedGroup(),
                    students);
        }
    }

    @Override
    public boolean add(Observable observable) {
        if (observable instanceof Student) {
            // System.out.println("MainWindow.add(Observable)");
            // System.out.println(observable.toString());
            this.groupTab.getModel().addRow((Student) observable);
            // this.cont.addStudent((Student) observable);
        }
        else if (observable instanceof Group) {
            ArrayList<Group> groups = this.cont.getGroupList();
            this.groupTab.updateGroupList(groups, (Group) observable);
        }
        else if (observable instanceof Exam) {
            //
        }
        return false;
    }

    @Override
    public boolean update(Observable observable) {
        if (observable instanceof Student) {
            this.groupTab.getModel().updateRow((Student) observable);
            // this.cont.updateStudent(observable);
        }
        else if (observable instanceof Group) {
            // this.groupTab.updateGroup((Group) observable);
            //
        }
        else if (observable instanceof Exam) {
            //
        }
        return false;
    }

    @Override
    public boolean delete(Observable observable) {
        if (observable instanceof Student) {
            this.groupTab.getModel().removeRow((Student) observable);
        }
        else if (observable instanceof Group) {
            //
        }
        else if (observable instanceof Exam) {
            //
        }
        return false;
    }

}
