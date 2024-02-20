package com.ecfingerprint.ecfingerprint.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "info")
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String dateOfBirth;
    private String nid10Digit;
    private String nid17Digit;


    @ElementCollection
    @CollectionTable(name = "finger_enums", joinColumns = @JoinColumn(name = "info_id"))
    private List<String> fingerEnums;

    public Info(String username, String password, String dateOfBirth, String nid10Digit, String nid17Digit, List<String> fingerEnums) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.nid10Digit = nid10Digit;
        this.nid17Digit = nid17Digit;
        this.fingerEnums = fingerEnums;
    }

    public Info() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNid10Digit() {
        return nid10Digit;
    }

    public void setNid10Digit(String nid10Digit) {
        this.nid10Digit = nid10Digit;
    }

    public String getNid17Digit() {
        return nid17Digit;
    }

    public void setNid17Digit(String nid17Digit) {
        this.nid17Digit = nid17Digit;
    }

    public List<String> getFingerEnums() {
        return fingerEnums;
    }

    public void setFingerEnums(List<String> fingerEnums) {
        this.fingerEnums = fingerEnums;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
