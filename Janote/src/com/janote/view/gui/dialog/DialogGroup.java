/**
 * 
 */
package com.janote.view.gui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.janote.controller.MainController;
import com.janote.model.entities.Group;
import com.janote.view.gui.MainWindow;

@SuppressWarnings("serial")
public class DialogGroup extends JDialog {
    private final boolean sendData = false;
    // private JLabel nomLabel, descLabel;
    private JTextField name;
    private JTextArea desc;

    private final MainController cont;
    private Group group;

    public DialogGroup(Group g, MainWindow parent) {
        super();

        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the
                                                                 // close cross
                                                                 // is clicked,
                                                                 // just close
                                                                 // the dialog
                                                                 // window

        this.setModal(true);
        this.setResizable(true);

        if (g == null) {
            this.group = new Group();
            this.setTitle("Enregistrer un nouveau groupe");
        }
        else {
            this.group = g;
            this.setTitle("Informations sur le groupe " + group.getId());
        }
        this.cont = parent.getController();

        this.initComponent();
    }

    public boolean showDialog() {
        this.setVisible(true);
        return this.sendData;
    }

    private void initComponent() {

        // group name
        JPanel panName = new JPanel();
        name = new JTextField(30);
        name.setText(this.group.getName());
        panName.setBorder(BorderFactory
                .createTitledBorder("Nom du nouveau groupe"));
        panName.add(name);

        // groups description
        desc = new JTextArea(10, 30);
        desc.setLineWrap(true);
        desc.setText(this.group.getDescription());

        JScrollPane panDesc = new JScrollPane(desc,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panDesc.setBorder(BorderFactory
                .createTitledBorder("Description (optionel)"));

        JPanel content = new JPanel();
        content.add(panName, BorderLayout.NORTH);
        content.add(panDesc, BorderLayout.CENTER);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");
        JButton cancelBouton = new JButton("Annuler");
        control.add(okBouton);
        control.add(cancelBouton);

        // content.add(control, BorderLayout.SOUTH);
        this.getContentPane().add(panName, BorderLayout.NORTH);
        this.getContentPane().add(panDesc, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);

        this.pack();

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Group gr = null;
                try {
                    gr = new Group(group.getId(), name.getText(), desc
                            .getText(), null, null);
                    gr.setModified(true);
                }
                catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Groupe invalide",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addGroup(gr);

                // if (cont.addOrUpdateGroup(gr)) {
                // JOptionPane.showMessageDialog(null,
                // "Le groupe a bien été ajouté.", "Succès",
                // JOptionPane.INFORMATION_MESSAGE);
                // sendData = true;
                // setVisible(false);
                // dispose();
                // }
                // else {
                // JOptionPane.showMessageDialog(null,
                // "L'ajout du groupe a échoué... ", "Erreur",
                // JOptionPane.ERROR_MESSAGE);
                // System.err.println("The group " + gr
                // + " couldn't be added....");
                // }
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

    public boolean addGroup(Group gr) {
        // System.out.println("DialogGroup.addGroup");
        // System.out.println(gr.toString());
        return this.cont.addOrUpdateGroup(gr);
    }
}