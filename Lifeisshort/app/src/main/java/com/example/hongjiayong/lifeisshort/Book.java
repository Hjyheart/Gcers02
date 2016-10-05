package com.example.hongjiayong.lifeisshort;

/**
 * Created by hongjiayong on 2016/10/5.
 */

public class Book {
    private String name;
    private String description;
    private String author;
    private String tag;
    private String owner;
    private String state;
    private String publisher;
    private int cover;

    public Book(){}

    public Book(String name, String description, String author, String tag, String owner, String state, String publisher, int cover){
        this.name = name;
        this.description = description;
        this.author = author;
        this.owner = owner;
        this.publisher = publisher;
        this.tag = tag;
        this.state = state;
        this.cover = cover;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}
