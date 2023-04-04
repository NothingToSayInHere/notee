package com.example.notee;

public class Note {
    private int id;
    private String title;
    private String content;
    //private Date createdAt;

    public Note() {
    }

//    public Note(int id, String title, String content, Date createdAt) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.createdAt = createdAt;
//    }


    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
}
