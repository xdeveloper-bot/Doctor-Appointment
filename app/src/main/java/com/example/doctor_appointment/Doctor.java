package com.example.doctor_appointment;

public class Doctor {

    private String FirstName,LastName,Email,Speciality,Pass;
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

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String Speciality) {
        this.Speciality = Speciality;
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
