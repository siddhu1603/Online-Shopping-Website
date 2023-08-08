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
public class FullProductDetail {
    private String prodid;
    private String prod_desc;
    private String custid;
    private String cust_name;
    private String crtdt;
    private int catid;
    private String maincat;
    private String subcat;
    
    private List<Integer> vslno;
    private List<String> variant;
    private List<Integer> qty;
    private List<Double> unitprice;
    private List<String> unitpricestring;
    private List<Integer> discount;
    private List<Integer> delivery;
    
    private List<Integer> islno;
    private List<String> filename;
    private List<String> path;
    
    private List<Integer> aslno;
    private List<String> about;
    
    private List<Integer> sslno;
    private List<String> spec;
    private List<String> specval;
    private List<String> unit;

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
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

    public String getSeller() {
        return cust_name;
    }

    public void setSeller(String seller) {
        this.cust_name = seller;
    }

    public String getCrtdt() {
        return crtdt;
    }

    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
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

    public List<Integer> getIslno() {
        return islno;
    }

    public void setIslno(List<Integer> islno) {
        this.islno = islno;
    }

    public List<String> getFilename() {
        return filename;
    }

    public void setFilename(List<String> filename) {
        this.filename = filename;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<Integer> getAslno() {
        return aslno;
    }

    public void setAslno(List<Integer> aslno) {
        this.aslno = aslno;
    }

    public List<String> getAbout() {
        return about;
    }

    public void setAbout(List<String> about) {
        this.about = about;
    }

    public List<Integer> getSslno() {
        return sslno;
    }

    public void setSslno(List<Integer> sslno) {
        this.sslno = sslno;
    }

    public List<String> getSpec() {
        return spec;
    }

    public void setSpec(List<String> spec) {
        this.spec = spec;
    }

    public List<String> getSpecval() {
        return specval;
    }

    public void setSpecval(List<String> specval) {
        this.specval = specval;
    }

    public List<String> getUnit() {
        return unit;
    }

    public void setUnit(List<String> unit) {
        this.unit = unit;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public List<Integer> getVslno() {
        return vslno;
    }

    public void setVslno(List<Integer> vslno) {
        this.vslno = vslno;
    }

    public List<String> getUnitpricestring() {
        return unitpricestring;
    }

    public void setUnitpricestring(List<String> unitpricestring) {
        this.unitpricestring = unitpricestring;
    }
    
}
