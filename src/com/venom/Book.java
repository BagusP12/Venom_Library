package com.venom;

import java.sql.Date;

class Book {

    private int id;
    private String title;
    private String author;
    private String genre;
    private Date publishingDate;
    private String publisher;

    Book(int id, String title, String author, String genre, Date publishingDate, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.setPublishingDate(publishingDate);
        this.publisher = publisher;
    }

    
    int getId() {
        return id;
    }
    
    void setId(int id) {
        this.id = id;
    }
    
    String getTitle() {
        return title;
    }
    
    void setTitle(String title) {
        this.title = title;
    }
    
    String getAuthor() {
        return author;
    }
    
    void setAuthor(String author) {
        this.author = author;
    }
    
    String getGenre() {
        return genre;
    }
    
    void setGenre(String genre) {
        this.genre = genre;
    }
    
    Date getPublishingDate() {
        return publishingDate;
    }
    
    void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }
    
    String getPublisher() {
        return publisher;
    }
    
    void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
}
