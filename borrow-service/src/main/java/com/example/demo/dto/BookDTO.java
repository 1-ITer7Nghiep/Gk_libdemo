package com.example.demo.dto;

public class BookDTO {
    private Long id;
    private String title;
    private int availableCopies;

    // Constructors, Getters, Setters
    public BookDTO() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}
