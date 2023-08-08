/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.model;

/**
 *
 * @author USER
 */
public class OrderDetails {

    private String orderid;
    private String customerid;
    private String customername;
    private String payment;
    private String prodid;
    private int itemno;
    private int qty;
    private double unitprice;
    private String uprice;
    private int discount;
    private double finalprice;
    private String sellprice;
    private int shipping;
    private String sellername;
    private String tranid;
    private String prod_desc;
    private String variant;
    private String walletname;
    private String aliasname;
    private String addr1;
    private String addr2;
    private String addr3;
    private String city;
    private String state;
    private String pincode;
    private String crtdt;
    private String path;
    private String filename;
    private int statusid;
    private String status;
    private String remark;
    private String statusdt;

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusdt() {
        return statusdt;
    }

    public void setStatusdt(String statusdt) {
        this.statusdt = statusdt;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getItemno() {
        return itemno;
    }

    public void setItemno(int itemno) {
        this.itemno = itemno;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public String getUprice() {
        return uprice;
    }

    public void setUprice(String uprice) {
        this.uprice = uprice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(double finalprice) {
        this.finalprice = finalprice;
    }

    public String getSellprice() {
        return sellprice;
    }

    public void setSellprice(String sellprice) {
        this.sellprice = sellprice;
    }

    public int getShipping() {
        return shipping;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getWalletname() {
        return walletname;
    }

    public void setWalletname(String walletname) {
        this.walletname = walletname;
    }

    public String getAliasname() {
        return aliasname;
    }

    public void setAliasname(String aliasname) {
        this.aliasname = aliasname;
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

    public String getCrtdt() {
        return crtdt;
    }

    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }

    public String getAddress() {
        String addr = getAddr1();
        if (getAddr2() != null) {
            addr += "<br>" + getAddr2();
        }
        if (getAddr3() != null) {
            addr += "<br>" + getAddr3();
        }
        addr += "<br>City - " + getCity() + "<br>State - " + getState() + "<br>Pincode - " + getPincode();
        return addr;
    }
}
