/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.model;

/**
 *
 * @author USER
 */
public class Address {
    private String custid;
    private int slno;
    private String alias_name;
    private String addr1;
    private String addr2;
    private String addr3;
    private String city;
    private String state;
    private String pincode;
    private String crtdt;

    public String getAddress(){
        String addr = getAddr1();
        if(getAddr2()!= null){
            addr += "<br>"+getAddr2();
        }
        if(getAddr3()!= null){
            addr += "<br>"+getAddr3();
        }
        addr += "<br>City - "+getCity()+"<br>State - "+getState()+"<br>Pincode - "+getPincode();
        return addr;
    }
    public String getCrtdt() {
        return crtdt;
    }

    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "Address{" + "custid=" + custid + ", slno=" + slno + ", alias_name=" + alias_name + ", addr1=" + addr1 + ", addr2=" + addr2 + ", addr3=" + addr3 + ", city=" + city + ", state=" + state + ", pincode=" + pincode + ", crtdt=" + crtdt + '}';
    }
}
