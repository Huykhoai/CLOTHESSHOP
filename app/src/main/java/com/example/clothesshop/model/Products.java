package com.example.clothesshop.model;

import java.io.Serializable;

public class Products implements Serializable {
    private int id;
    private String name;
    private int inventory;
    private int price;
    private String image;
    private String description;
    private int id_Category;

    public Products(int id, String name, int inventory, int price, String image,String description, int id_Category) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.price = price;
        this.image = image;
        this.description = description;
        this.id_Category = id_Category;
    }

    public Products() {
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

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_Category() {
        return id_Category;
    }

    public void setId_Category(int id_Category) {
        this.id_Category = id_Category;
    }
}
