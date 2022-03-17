package com.example.yrocery.POJO;

public class CardItem {
    String name;
    String price;
    String image;
    String amount;

    public CardItem() {
    }

    public CardItem(String name, String price, String image, String amount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.amount = amount;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
