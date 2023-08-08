/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.model;

/**
 *
 * @author USER
 */
public class User {
    private String custid;
    private String username;
    private String mobile;
    private String dob;
    private String pwd;
    private String crtdt;
    private String userid;
    private String email;
    private String gender;
    private String custtype;
    private String active;
    private String inactivedt;
    private Boolean isUserValid;
    
    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCrtdt() {
        return crtdt;
    }

    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getInactivedt() {
        return inactivedt;
    }

    public void setInactivedt(String inactivedt) {
        this.inactivedt = inactivedt;
    }

    public Boolean getIsUserValid() {
        return isUserValid;
    }

    public void setIsUserValid(Boolean isUserValid) {
        this.isUserValid = isUserValid;
    }
}
