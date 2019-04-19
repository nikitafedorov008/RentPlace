package com.polka.rentplace.model;

public class Products {

    private String pname, description, price, ptime, image, category, pid, date, time, phone;
    private String sname, ssurname, sphone;
    public Products(){

    }

    public Products(String pname,String phone, String description, String price, String ptime, String image, String category, String pid, String date, String time,
          String ssname, String ssurname) {
        this.pname = pname;
        this.phone = phone;
        this.description = description;
        this.price = price;
        this.ptime = ptime;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;

        this.sname = ssname;
        this.ssurname = ssurname;
        this.sphone = sphone;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
