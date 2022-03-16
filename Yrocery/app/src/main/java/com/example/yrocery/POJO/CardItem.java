package com.example.yrocery.POJO;

public class CardItem {
    String productName;
    String productPrice;
    String productImg;
    String productAmount;

    public CardItem() {
    }

    public CardItem(String productName, String productPrice, String productImg, String productAmount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productAmount = productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }
}
