package com.example.grocery;

public class AddressesModel {

    private String fullname,addess,pincode;
    private boolean selected;

    public AddressesModel(String fullname, String addess, String pincode,boolean selected) {
        this.fullname = fullname;
        this.addess = addess;
        this.pincode = pincode;
        this.selected=selected;
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

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
