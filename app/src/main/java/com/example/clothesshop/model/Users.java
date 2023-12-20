package com.example.clothesshop.model;

public class Users {
    private int id;
    private String name;
    private String pass;
    private int phone;
    private String address;
    private int role;
    private String avatar;

    public Users() {
    }

    public Users(int id, String name, String pass, int phone, String address, int role, String avatar) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
