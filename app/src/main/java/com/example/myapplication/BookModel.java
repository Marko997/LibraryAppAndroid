package com.example.myapplication;


public class BookModel {
    private String title, category, author,excerpt,image;

    public BookModel() {
    }

    public BookModel(String title, String category, String author, String excerpt, String image) {
        this.title = title;
        this.category = category;
        this.author = author;
        this.excerpt = excerpt;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }




}
