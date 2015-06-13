/**
 * 
 */
package com.janote.view.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

@SuppressWarnings("serial")
public class DialogGroup extends JDialog {
    private final boolean sendData = false;
    // private JLabel nomLabel, descLabel;
    private JTextField name;
    private JTextArea desc;

    private final MainController cont;
    private Group group;

    public DialogGroup(Group g, MainController cont) {
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

        if (g == null) {
            this.group = new Group();
            this.setTitle("Enregistrer un nouveau groupe");
        }
        else {
            this.group = g;
            this.setTitle("Informations sur le groupe " + group.getId());
        }
        this.cont = cont;

        this.initComponent();
    }

    public boolean showDialog() {
        this.setVisible(true);
        return this.sendData;
    }

    private void initComponent() {

        // group name
        JPanel panName = new JPanel();
        // panNom.setBackground(Color.white);
        panName.setPreferredSize(new Dimension(400, 70));
        name = new JTextField();
        name.setPreferredSize(new Dimension(350, 25));
        name.setText(this.group.getName());
        panName.setBorder(BorderFactory
                .createTitledBorder("Nom du nouveau groupe"));
        // nomLabel = new JLabel("Saisir un nom :");
        // panNom.add(nomLabel);
        panName.add(name);

        // groups description
        desc = new JTextArea();
        desc.setPreferredSize(new Dimension(350, 200));
        desc.setText(this.group.getDescription());

        JScrollPane panDesc = new JScrollPane(desc,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panDesc.setPreferredSize(new Dimension(400, 400));
        panDesc.setBorder(BorderFactory
                .createTitledBorder("Description (optionel)"));
        // panDesc.add(desc);

        JPanel content = new JPanel();
        // content.setBackground(Color.white);
        content.add(panName);
        content.add(panDesc);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");
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

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        control.add(okBouton);
        control.add(cancelBouton);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }

    public boolean addGroup(Group gr) {
        // System.out.println("DialogGroup.addGroup");
        // System.out.println(gr.toString());
        return this.cont.addOrUpdateGroup(gr);
    }
}