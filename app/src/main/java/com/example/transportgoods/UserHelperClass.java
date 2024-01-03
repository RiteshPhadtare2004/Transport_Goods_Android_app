package com.example.transportgoods;

public class UserHelperClass {

    String Pick_Up_Point_of_Good,Drop_Point_of_Good,vehical_Good_can_be_travelled,good_address,good_address_drop,name_of_good,phone_no;

    public UserHelperClass() {
    }

    public UserHelperClass(String pick_Up_Point_of_Good, String drop_Point_of_Good, String vehical_Good_can_be_travelled, String good_address, String good_address_drop, String name_of_good, String phone_no) {
        Pick_Up_Point_of_Good = pick_Up_Point_of_Good;
        Drop_Point_of_Good = drop_Point_of_Good;
        this.vehical_Good_can_be_travelled = vehical_Good_can_be_travelled;
        this.good_address = good_address;
        this.good_address_drop = good_address_drop;
        this.name_of_good = name_of_good;
        this.phone_no = phone_no;
    }

    public String getPick_Up_Point_of_Good() {
        return Pick_Up_Point_of_Good;
    }

    public void setPick_Up_Point_of_Good(String pick_Up_Point_of_Good) {
        Pick_Up_Point_of_Good = pick_Up_Point_of_Good;
    }

    public String getDrop_Point_of_Good() {
        return Drop_Point_of_Good;
    }

    public void setDrop_Point_of_Good(String drop_Point_of_Good) {
        Drop_Point_of_Good = drop_Point_of_Good;
    }

    public String getVehical_Good_can_be_travelled() {
        return vehical_Good_can_be_travelled;
    }

    public void setVehical_Good_can_be_travelled(String vehical_Good_can_be_travelled) {
        this.vehical_Good_can_be_travelled = vehical_Good_can_be_travelled;
    }

    public String getGood_address() {
        return good_address;
    }

    public void setGood_address(String good_address) {
        this.good_address = good_address;
    }

    public String getGood_address_drop() {
        return good_address_drop;
    }

    public void setGood_address_drop(String good_address_drop) {
        this.good_address_drop = good_address_drop;
    }

    public String getName_of_good() {
        return name_of_good;
    }

    public void setName_of_good(String name_of_good) {
        this.name_of_good = name_of_good;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
