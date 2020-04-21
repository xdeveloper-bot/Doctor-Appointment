package com.example.doctor_appointment;

public class Doctor {

    private String FirstName,LastName,Email,DOB,Pass;
    private Long Mob;
    public Doctor() {

    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public Long getMob() {
        return Mob;
    }

    public void setMob(Long mob) {
        Mob = mob;
    }
}
