/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.model;

import java.util.List;

/**
 *
 * @author USER
 */
public class ProductVariant {
    private String prodid;
    private int catid;
    private String prod_desc;
    private String custid;
    private String pflag;
    private String pcrtdt;
    private List<Integer> slno;
    private List<String> variant;
    private List<Integer> qty;
    private List<Double> unitprice;
    private List<Integer> discount;
    private List<Integer> delivery;

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getPflag() {
        return pflag;
    }

    public void setPflag(String pflag) {
        this.pflag = pflag;
    }

    public String getPcrtdt() {
        return pcrtdt;
    }

    public void setPcrtdt(String pcrtdt) {
        this.pcrtdt = pcrtdt;
    }

    public List<Integer> getSlno() {
        return slno;
    }

    public void setSlno(List<Integer> slno) {
        this.slno = slno;
    }

    public List<String> getVariant() {
        return variant;
    }

    public void setVariant(List<String> variant) {
        this.variant = variant;
    }

    public List<Integer> getQty() {
        return qty;
    }

    public void setQty(List<Integer> qty) {
        this.qty = qty;
    }

    public List<Double> getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(List<Double> unitprice) {
        this.unitprice = unitprice;
    }

    public List<Integer> getDiscount() {
        return discount;
    }

    public void setDiscount(List<Integer> discount) {
        this.discount = discount;
    }

    public List<Integer> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<Integer> delivery) {
        this.delivery = delivery;
    }
}
