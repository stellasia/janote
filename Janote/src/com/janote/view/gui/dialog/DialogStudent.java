/**
 * 
 */
package com.janote.view.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.janote.controller.MainController;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Gender;
import com.janote.model.entities.Student;

/**
 * @author estelle
 * 
 */
@SuppressWarnings("serial")
public class DialogStudent extends JDialog {

    public static final int LABEL_WIDTH = 300;
    public static final int LABEL_HEIGHT = 30;

    private Student student;
    private final MainController cont;

    private JTextField name, surname, birth, email;
    private JComboBox<Gender> gender;
    private JCheckBox repeating;

    private DialogStatus status;

    public DialogStudent(Student stu, MainController cont) {
        super();

        this.setModal(true); // VERY IMPORTANT ! Allow to block any other input,
                             // ie allow to get the relevant return value in
                             // showDialog

        if (stu == null) {
            this.student = new Student();
            this.setTitle("Ajouter un nouvel étudiant");
        }
        else {
            this.student = stu;
            this.setTitle("Informations sur l'étudiant  " + student.getId());
        }
        this.cont = cont;

        this.setSize(500, 270);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the
                                                                 // close
                                                                 // cross is
                                                                 // clicked,
                                                                 // just
                                                                 // close the
                                                                 // dialog
                                                                 // window

        this.status = DialogStatus.NOTHING_CHANGE; // default is everything OK
                                                   // status
        this.initComponent();
    }

    public DialogStatus showDialog() {
        this.setVisible(true);
        // System.out.println("In DialogStudent.showDialog --> " + this.status);
        return this.status;
    }

    private void initComponent() {

        JPanel panGen = new JPanel();
        // panGen.setBackground(Color.white);
        panGen.setLayout(new GridLayout(3, 1));
        panGen.setPreferredSize(new Dimension(DialogStudent.LABEL_WIDTH * 3,
                DialogStudent.LABEL_HEIGHT * 20));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel outerPanNom = new JPanel();
        outerPanNom.setLayout(new GridBagLayout());
        outerPanNom.setBorder(BorderFactory.createTitledBorder("Général"));
        name = new JTextField();
        name.setSize(new Dimension(DialogStudent.LABEL_WIDTH,
                DialogStudent.LABEL_HEIGHT));
        name.setText(this.student.getName());
        JLabel nomLabel = new JLabel("Nom : ");
        Font font = nomLabel.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD,
                font.getSize() + 2);
        nomLabel.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        outerPanNom.add(nomLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        outerPanNom.add(name, c);

        surname = new JTextField();
        surname.setSize(new Dimension(DialogStudent.LABEL_WIDTH,
                DialogStudent.LABEL_HEIGHT));
        surname.setText(this.student.getSurname());
        JLabel prenomLabel = new JLabel("Prénom : ");
        prenomLabel.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        outerPanNom.add(prenomLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        outerPanNom.add(surname, c);

        gender = new JComboBox<Gender>();
        gender.addItem(Gender.BOY);
        gender.addItem(Gender.GIRL);
        gender.setSelectedItem(this.student.getGender());
        JLabel genderLabel = new JLabel("Sexe : ");
        genderLabel.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        outerPanNom.add(genderLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        outerPanNom.add(gender, c);
        panGen.add(outerPanNom);

        birth = new JTextField();
        birth.setPreferredSize(new Dimension(DialogStudent.LABEL_WIDTH,
                DialogStudent.LABEL_HEIGHT));
        birth.setText(this.student.getBirthdayAsString());
        JLabel birthLabel = new JLabel("Date de naissance : ");
        birthLabel.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        outerPanNom.add(birthLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        outerPanNom.add(birth, c);

        email = new JTextField();
        email.setPreferredSize(new Dimension(DialogStudent.LABEL_WIDTH,
                DialogStudent.LABEL_HEIGHT));
        email.setText(this.student.getEmail());
        JLabel emailLabel = new JLabel("Adresse e-mail : ");
        emailLabel.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        outerPanNom.add(emailLabel, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        outerPanNom.add(email, c);

        panGen.add(outerPanNom, BorderLayout.NORTH);

        JPanel outerPanGrades = new JPanel();
        outerPanGrades.setBorder(BorderFactory.createTitledBorder("Scolarité"));
        outerPanGrades.setLayout(new GridBagLayout());
        repeating = new JCheckBox();
        repeating.setPreferredSize(new Dimension(DialogStudent.LABEL_WIDTH,
                DialogStudent.LABEL_HEIGHT));
        repeating.setSelected(this.student.isRepeating());
        JLabel labelRepeating = new JLabel("Redoublant : ");
        labelRepeating.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        outerPanGrades.add(labelRepeating, c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        outerPanGrades.add(repeating, c);
        if (student.getExamsGrades() != null) {
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 4;
            outerPanGrades.add(separator, c);

            int i = 2;
            for (Map.Entry<Exam, Float> entry : student.getExamsGrades()
                    .entrySet()) {
                JTextField g = new JTextField();
                g.setPreferredSize(new Dimension(DialogStudent.LABEL_WIDTH,
                        DialogStudent.LABEL_HEIGHT));
                g.setText(entry.getValue().toString());
                JLabel gLabel = new JLabel(entry.getKey().getName());
                gLabel.setFont(boldFont);
                c.gridx = 0;
                c.gridy = i;
                c.gridwidth = 1;
                outerPanGrades.add(gLabel, c);
                c.gridx = 1;
                c.gridy = i;
                c.gridwidth = 2;
                outerPanGrades.add(g, c);
                i++;
            }
        }
        panGen.add(outerPanGrades, BorderLayout.CENTER);

        JPanel panBtn = new JPanel();
        JButton saveButton = new JButton("OK");
        saveButton.setPreferredSize(new Dimension(
                DialogStudent.LABEL_WIDTH / 2, DialogStudent.LABEL_HEIGHT));
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setPreferredSize(new Dimension(
                DialogStudent.LABEL_WIDTH / 2, DialogStudent.LABEL_HEIGHT));
        /*
         * c.gridx = 1; c.gridy = 5; c.gridwidth = 1;
         */
        panBtn.add(saveButton, BorderLayout.LINE_START);
        /*
         * c.gridx = 2; c.gridy = 5; c.gridwidth = 1;
         */
        panBtn.add(cancelButton, BorderLayout.LINE_END);

        panGen.add(panBtn, BorderLayout.SOUTH);

        this.getContentPane().add(panGen, BorderLayout.CENTER);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // setVisible(false);
                dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Student stu = null;
                try {
                    stu = new Student(student.getId(), name.getText(), surname
                            .getText(), (Gender) gender.getSelectedItem(),
                            email.getText(), birth.getText(), repeating
                                    .isSelected(), cont.getSelectedGroup()
                                    .getId());
                    stu.setModified(true);
                }
                catch (IllegalArgumentException e) {
                    status = DialogStatus.ERROR; // the Dialog status is no more
                                                 // "everything is ok"
                    JOptionPane.showMessageDialog(null, "Etudiant invalide",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addOrUpdateStudent(stu);

                setVisible(false);
                dispose();
            }

        });

    }

    public boolean addOrUpdateStudent(Student stu) {
        // System.out.println("DialogStudent.addStudent");
        // System.out.println(stu.toString());
        if (this.student.getId() == null)
            this.cont.addStudent(stu);
        else
            this.cont.updateStudent(stu);
        // student = stu;
        return true;
    }
}
