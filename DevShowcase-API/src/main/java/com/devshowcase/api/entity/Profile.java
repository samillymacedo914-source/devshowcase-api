package com.devshowcase.api.entity;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="profiles")
public class Profile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String name;
 @Column(nullable=false,length=500) private String bio;
 @Column(length=255) private String githubUrl;
 @Column(length=255) private String linkedinUrl;
 @OneToMany(mappedBy="profile") private List<Project> projects=new ArrayList<>();
 public Long getId(){return id;} public String getName(){return name;} public String getBio(){return bio;}
 public String getGithubUrl(){return githubUrl;} public String getLinkedinUrl(){return linkedinUrl;} public List<Project> getProjects(){return projects;}
 public void setName(String v){name=v;} public void setBio(String v){bio=v;} public void setGithubUrl(String v){githubUrl=v;} public void setLinkedinUrl(String v){linkedinUrl=v;}
}
