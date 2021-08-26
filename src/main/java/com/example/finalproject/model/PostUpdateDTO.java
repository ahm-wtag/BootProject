package com.example.finalproject.model;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class PostUpdateDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    public Long id;

    @Column(name = "post_title")
    public String title;

    @Column(name = "post_body")
    public String body;

    public PostUpdateDTO() {
    }

    public PostUpdateDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long post_id) {
        this.id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }


}
