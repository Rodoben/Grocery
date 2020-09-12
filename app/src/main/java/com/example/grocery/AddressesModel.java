package com.example.grocery;

public class AddressesModel {

    private String fullname,addess,pincode;

    public AddressesModel(String fullname, String addess, String pincode) {
        this.fullname = fullname;
        this.addess = addess;
        this.pincode = pincode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddess() {
        return addess;
    }

    public void setAddess(String addess) {
        this.addess = addess;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
