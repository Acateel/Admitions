package com.adminitions.entities;

import java.sql.Blob;

public class Applicant extends Entity {
    private String lastName;
    private String name;
    private String surname;
    private String email;
    private String city;
    private String region;
    private String nameEducationalInstitution;
    private Blob attestation;
    private boolean block = false;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNameEducationalInstitution() {
        return nameEducationalInstitution;
    }

    public void setNameEducationalInstitution(String nameEducationalInstitution) {
        this.nameEducationalInstitution = nameEducationalInstitution;
    }

    public Blob getAttestation() {
        return attestation;
    }

    public void setAttestation(Blob attestation) {
        this.attestation = attestation;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", nameEducationalInstitution='" + nameEducationalInstitution + '\'' +
                ", attestation=" + attestation +
                ", block=" + block +
                '}';
    }
}
