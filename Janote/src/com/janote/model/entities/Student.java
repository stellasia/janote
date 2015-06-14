package com.janote.model.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class to describe a student. Members: - id - name - surname - gender (Boy or
 * Girl, @see Gender) - email - birthday (Date with format YYY-MM-DD) -
 * repeating (boolean) - groupID (the group the student belongs to)
 * 
 * Only the id and name are mandatory.
 * 
 * @author Estelle Scifo
 * @version 1.0
 */

public class Student extends AbsEntity {

    protected String name = null;
    protected String surname = null;
    protected Gender gender = null;
    protected String email = null;
    protected Date birthday = null;
    protected Boolean repeating = false;
    protected Float average_grade = null;
    protected Integer group_id = null;

    /**
     * Exam to grade map.
     */
    HashMap<Exam, Float> exams;

    public Student() {
    };

    /**
     * @param id
     * @param name
     * @param surname
     * @param gender
     * @param email
     * @param birthday
     * @param repeating
     * @param groupID
     */
    public Student(Integer id, String name, String surname, Gender gender,
            String email, String birthday, Boolean repeating, Integer groupID) {
        super();
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setGender(gender);
        this.setEmail(email);
        this.setBirthday(birthday);
        this.setRepeating(repeating);
        this.setGroup_id(groupID);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Get the birthday date as a String with format "dd-MM-yyyy"
     * 
     * @return the birthday date "dd-MM-yyyy"
     */
    public String getBirthdayAsString() {
        if (this.birthday == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        // System.out.println("Get birthday as string " +
        // this.birthday.toString() + " " + sdf.format(this.birthday));
        return sdf.format(this.birthday);
    }

    /**
     * @return the repeating
     */
    public boolean isRepeating() {
        return repeating;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Computes the student average grade and returns it.
     * 
     * @return the average_grade
     */
    public Float getAverage_grade() {
        if (this.average_grade == null) {
            this.compute_average_grade();
        }
        return this.average_grade;
    }

    /**
     * Computes the student average grade :
     * 
     * average = sum_e ( grade_e * coeff_e) / sum_e (coeff_e) for e in exams
     * 
     */
    public void compute_average_grade() {
        if (this.exams == null || this.exams.size() <= 0) {
            this.average_grade = null;
            return;
        }

        float mean = 0;
        float weight = 0;

        Iterator it = this.exams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            float coeff = ((Exam) pair.getKey()).getCoefficient();
            mean += coeff * (float) pair.getValue();
            weight += coeff;
        }

        this.average_grade = new Float(mean / weight);
    }

    /**
     * @return a list of all exams the student is supposed to have taken
     */
    public ArrayList<Exam> getExams() {
        ArrayList<Exam> list = new ArrayList<Exam>();
        list.addAll(exams.keySet());
        return list;
    }

    public HashMap<Exam, Float> getExamsGrades() {
        return exams;
    }

    /**
     * @return a list of all grades
     */
    public List<Float> getGrades() {
        return new ArrayList<Float>(exams.values());
    }

    public Float getGrade(Exam e) {
        return this.exams.get(e);
    }

    /**
     * Set the student name.
     * 
     * If the name parameter is not valid (null or empty String or single space
     * String), throws a IllegalArgumentExcption.
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        if (name != null && name.length() > 0) {
            this.name = name;
        }
        else
            throw new IllegalArgumentException(
                    "Le nom d'un étudiant doit être rempli !");
    }

    /**
     * @param surname
     *            the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(Date birthday) {
        // System.out.println("Student.setBirthday " + birthday.toString());
        this.birthday = birthday;
    }

    /**
     * Set the birthday date from a String with format "dd-MM-YYYY". If format
     * is incorrect, Student.birthday will not be changed. If paramter is null,
     * birthday will be set to null.
     * 
     * @param date
     *            the birthday date as a string with format "dd-MM-YYYY"
     */
    public void setBirthday(String date) {
        // System.out.println("Student.setBirthday (string)  " + date);
        if (date == null || date.equals("")) {
            this.birthday = null;
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.birthday = sdf.parse(date);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            this.birthday = null;
        }
    }

    /**
     * @param repeating
     *            : true if the Student is repeating the same class.
     */
    public void setRepeating(Boolean repeating) {
        this.repeating = repeating;
    }

    /**
     * @param average_grade
     *            the average_grade to set
     */
    public void setAverage_grade(Float average_grade) {
        this.average_grade = average_grade;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @param exams
     *            the exams to set
     */
    public void setExams(ArrayList<Exam> exams) {
        if (exams == null)
            return;
        if (this.exams == null)
            this.exams = new HashMap<Exam, Float>();
        for (Exam e : exams) {
            this.exams.put(e, (float) -1);
        }
    }

    public void setExamGrades(HashMap<Exam, Float> exams) {
        this.exams = exams;
    }

    /**
     * @return the group_id
     */
    public Integer getGroup_id() {
        return group_id;
    }

    /**
     * @param group_id
     *            the group_id to set
     */
    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    /**
     * Set the grade of a Student to a specific Exam.
     * 
     * @param e
     *            the exam
     * @param val
     *            the grade value obtained by the Student to that Exam
     */
    public void setGrade(Exam e, Float val) {
        this.exams.put(e, val);
    }

    @Override
    public String toString() {
        final int maxLen = 10;
        return "Student [id=" + id + ", name=" + name + ", surname=" + surname
                + ", gender=" + gender + ", email=" + email + ", birthday="
                + birthday + ", repeating=" + repeating + ", average_grade="
                + average_grade + ", exams="
                + (exams != null ? toString(exams.entrySet(), maxLen) : null)
                + "]";
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

}
