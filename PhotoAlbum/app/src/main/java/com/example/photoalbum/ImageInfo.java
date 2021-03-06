package com.example.photoalbum;

public class ImageInfo {
    private String title;
    private String description;
    private String image;

    public ImageInfo(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public ImageInfo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
