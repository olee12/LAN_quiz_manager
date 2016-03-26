/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeededClass;

import java.io.Serializable;
import java.util.Vector;

/**
 * this class contain data of a student ,like ID number , name, registration
 * number , password .
 *
 * @author Tahmid
 */
public class Student implements Serializable {

    private int ID; /// student ID
    private String name; // name of student 
    private String registrationNo; // student registration number
    private String password; /// password of the student

    /**
     * empty constructor
     */
    public Student() {

    }

    /**
     * constructor with student data with out password
     *
     * @param ID student id
     * @param reg student registration number
     * @param name student name
     * @param pass student password
     */
    public Student(int ID, String reg, String name, String pass) {
        this.ID = ID;
        this.name = name;
        this.registrationNo = reg;
        this.password = pass;
    }

    /**
     * constructor without student data with out password
     *
     * @param ID student id
     * @param reg student registration number
     * @param name student name
     *
     */
    public Student(int ID, String reg, String name) {
        this.ID = ID;
        this.name = name;
        this.registrationNo = reg;
    }

    /**
     * get student name
     *
     * @return student name
     */
    public String getStudentName() {
        return name;
    }

    /**
     * get student registration number
     *
     * @return student registration number
     */
    public String getStudentRegNo() {
        return registrationNo;
    }
    /**
     * get student's password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * get student's id
     * @return student's id
     */
    public int getID() {
        return this.ID;
    }
    /**
     * set student id
     * @param ID student id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * set student name
     *
     * @param name student name
     */
    public void setStudentName(String name) {
        this.name = name;
    }

    /**
     * set student registration number
     *
     * @param regNo student registration number
     */
    public void setStudentRegNo(String reg) {
        this.registrationNo = reg;
    }
    /**
     * set student's password
     * @param pass password
     */
    public void setPassword(String pass) {
        this.password = pass;
    }

}
