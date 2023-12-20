package com.example.clothesshop.model;

import java.io.Serializable;

public class Photo implements Serializable {
    int image;

    public Photo(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
