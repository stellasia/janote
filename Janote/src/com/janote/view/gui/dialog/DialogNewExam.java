package com.janote.view.gui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.janote.controller.MainController;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;
import com.janote.view.gui.table.GroupGrades;

public class DialogNewExam extends JDialog {

    private final Integer TEXT_FIELD_SIZE = 20;

    private final boolean sendData = false;

    private JTextField name;
    private JTextArea desc;
    private JTextField coeff;
    private GroupGrades gg;

    private final MainController cont;
    private Exam exam;
    private final Group group;
    private final DialogStatus status;

    public DialogNewExam(Exam e, Group g, MainController cont) {
        super();

        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the
                                                                 // close cross
                                                                 // is clicked,
                                                                 // just close
                                                                 // the dialog
                                                                 // window

        this.setModal(true);

        this.group = g;

        if (e.getId() == null) {
            this.exam = new Exam();
            this.setTitle("Enregistrer un nouvel exam");
        }
        else {
            this.exam = e;
            this.setTitle("Informations sur l'exam " + e.getId());
        }
        this.cont = cont;

        this.status = DialogStatus.NOTHING_CHANGE;

        this.initComponent();
    }

    public DialogStatus showDialog() {
        this.setVisible(true);
        // System.out.println("In DialogStudent.showDialog --> " + this.status);
        return this.status;
    }

    private void initComponent() {

        JTabbedPane content = new JTabbedPane();

        // Exam informations
        JPanel infos = new JPanel();
        infos.setLayout(new BoxLayout(infos, BoxLayout.PAGE_AXIS));
        // exam name
        JPanel panName = new JPanel();
        name = new JTextField(TEXT_FIELD_SIZE);
        name.setText(this.exam.getName());
        panName.setBorder(BorderFactory.createTitledBorder("Titre de l'examen"));
        panName.add(name);

        // exam description
        desc = new JTextArea(10, TEXT_FIELD_SIZE);
        desc.setLineWrap(true);
        desc.setText(this.exam.getDescription());

        JScrollPane panDesc = new JScrollPane(desc,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panDesc.setBorder(BorderFactory
                .createTitledBorder("Description (optionel)"));

        // exam coefficient
        coeff = new JTextField(TEXT_FIELD_SIZE);
        coeff.setText(this.exam.getCoefficient().toString());

        JPanel panCoeff = new JPanel();
        panCoeff.setBorder(BorderFactory.createTitledBorder("Coefficient"));
        panCoeff.add(coeff);

        // add all sub-pan to the content
        infos.add(panName);
        infos.add(panDesc);
        infos.add(panCoeff);
        // add a new tab
        content.add("Informations", infos);

        // Student grade list
        JPanel studentGrades = new JPanel();
        gg = new GroupGrades(this, this.group.getStudentGrades(this.exam));
        studentGrades.add(gg);
        content.add("Notes", studentGrades);

        // Control buttons
        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");
        JButton cancelBouton = new JButton("Annuler");
        control.add(okBouton);
        control.add(cancelBouton);

        this.getContentPane().add(content, BorderLayout.NORTH);
        this.getContentPane().add(control, BorderLayout.SOUTH);

        this.pack();

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Exam e = null;
                HashMap<Student, Float> grades;
                try {
                    Float c = Float.parseFloat(coeff.getText());
                    e = new Exam(exam.getId(), name.getText(), desc.getText(),
                            c, cont.getSelectedGroup().getId());
                    grades = gg.getGrades();
                }
                catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, "Exam invalide",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addOrUpdateExam(e);

                setVisible(false);
                dispose();
            }
        });

        cancelBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

    }

    public boolean addOrUpdateExam(Exam e) {
        if (this.exam.getId() == null)
            this.cont.addExam(this.exam);
        else
            this.cont.updateExam(this.exam);
        return true;
    }
}