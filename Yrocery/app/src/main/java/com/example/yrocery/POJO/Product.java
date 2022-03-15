package com.example.yrocery.POJO;

public class Product {
    String name;
    String price;
    String image;

    public Product() {
    }

    public Product(String name, String price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.image = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return image;
    }

    public void setImgUrl(String imgUrl) {
        this.image = imgUrl;
    }
}
