/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.model;

/**
 *
 * @author USER
 */
public class Wallet {
    private String custid;
    private int slno;
    private String wallet_name;
    private double amount;
    private String amountstring;
    private String flag;
    private String crtdt;
    private String lastusedt;

    public String getLastusedt() {
        return lastusedt;
    }

    public void setLastusedt(String lastusedt) {
        this.lastusedt = lastusedt;
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

    public String getWallet_name() {
        return wallet_name;
    }

    public void setWallet_name(String wallet_name) {
        this.wallet_name = wallet_name;
    }

    public String getAmountstring() {
        return amountstring;
    }

    public void setAmountstring(String amountstring) {
        this.amountstring = amountstring;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCrtdt() {
        return crtdt;
    }

    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }
}
