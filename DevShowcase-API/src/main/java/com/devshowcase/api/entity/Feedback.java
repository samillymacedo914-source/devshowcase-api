package com.devshowcase.api.entity;
import jakarta.persistence.*;
@Entity @Table(name="feedbacks")
public class Feedback {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String author;
 @Column(nullable=false,length=1000) private String comment;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="project_id") private Project project;
 public Long getId(){return id;} public String getAuthor(){return author;} public String getComment(){return comment;} public Project getProject(){return project;}
}
