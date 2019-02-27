package com.nudle.rentplace.model;

public class Cart {

    private String pid, pname, price, quantity, discount, ptime;
    public static String sname, ssurname, sphone;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quantity, String discount, String ptime,
                String sname, String ssurname, String sphone) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.ptime = ptime;

        this.sname = sname;
        this.ssurname = ssurname;
        this.sphone = sphone;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsurname() {
        return ssurname;
    }

    public void setSsurname(String ssurname) {
        this.ssurname = ssurname;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }
}
