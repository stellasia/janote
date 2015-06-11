/**
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
package com.janote.model.entities;

import java.util.ArrayList;

/**
 * @author estelle
 * 
 */
public class Teacher extends AbsEntity {

    protected String name;
    protected String surname;

    protected ArrayList<Group> groups;

    public Teacher() {
    }

    public Teacher(Integer id, String name, String surname,
            ArrayList<Group> groups) {
        this.id = id;
        this.name = name;
        this.surname = surname;

        this.groups = groups;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
        this.observableUpdated(this);
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname
     *            the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
        this.observableUpdated(this);
    }

    /**
     * @return the groups
     */
    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
        this.observableUpdated(this);
    }

    public void addGroup(Group g) {
        if (this.groups == null)
            this.groups = new ArrayList<Group>();
        g.setTeacher_id(this.getId());
        this.groups.add(g);
        this.observableAdded(g);
    }
}
