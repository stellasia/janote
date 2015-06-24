/**
 * 
 */
package com.janote.view.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.janote.view.gui.MainWindow;

/**
 * @author estelle
 * 
 */
@SuppressWarnings("serial")
public class DialogStudent extends JDialog {

    // public static final int LABEL_WIDTH = 150;
    // public static final int LABEL_HEIGHT = 30;

    private final Integer TEXT_FIELD_SIZE = 20;

    private Student student;
    private final MainController cont;

    private JTextField name, surname, birth, email;
    private JComboBox<Gender> gender;
    private JCheckBox repeating;

    private DialogStatus status;

    public DialogStudent(Student stu, MainWindow parent) {
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
        this.cont = parent.getController();

        this.setLocationRelativeTo(parent);
        this.setResizable(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // when the close cross is clicked, just close the dialog window

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

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel outerPanNom = new JPanel();
        outerPanNom.setLayout(new GridBagLayout());
        outerPanNom.setBorder(BorderFactory.createTitledBorder("Général"));
        name = new JTextField(TEXT_FIELD_SIZE);
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

        surname = new JTextField(TEXT_FIELD_SIZE);
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

        birth = new JTextField(TEXT_FIELD_SIZE);
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

        email = new JTextField(TEXT_FIELD_SIZE);
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

        JTextField average = new JTextField(10);
        average.setText(this.student.getAverage_grade().toString());
        average.setEditable(false);
        average.setForeground(Color.RED);
        JLabel labelAverage = new JLabel("Moyenne : ");
        labelAverage.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        outerPanGrades.add(labelAverage, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        outerPanGrades.add(average, c);

        if (student.getExamsGrades() != null) {
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 4;
            outerPanGrades.add(separator, c);

            int i = 3;
            for (Map.Entry<Exam, Float> entry : student.getExamsGrades()
                    .entrySet()) {
                JTextField g = new JTextField();
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
        JButton cancelButton = new JButton("Annuler");
        /*
         * c.gridx = 1; c.gridy = 5; c.gridwidth = 1;
         */
        panBtn.add(saveButton, BorderLayout.LINE_START);
        /*
         * c.gridx = 2; c.gridy = 5; c.gridwidth = 1;
         */
        panBtn.add(cancelButton, BorderLayout.LINE_END);

        panGen.add(panBtn, BorderLayout.SOUTH);

        this.getContentPane().add(outerPanNom, BorderLayout.NORTH);
        this.getContentPane().add(outerPanGrades, BorderLayout.CENTER);
        this.getContentPane().add(panBtn, BorderLayout.SOUTH);

        this.pack();

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
