package com.example.demo.model;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = -2749977649306134186L;

    private String ID;
    private String name;
    private String school;

    public Student() {
    }

    public Student(String ID, String name, String school) {
        this.ID = ID;
        this.name = name;
        this.school = school;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString()
    {
        return String.format("Student [ID = %s, Name = %s, School = %s]", ID, name, school);
    }
}
