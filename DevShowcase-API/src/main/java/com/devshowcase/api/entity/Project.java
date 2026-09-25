package com.devshowcase.api.entity;
import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="projects")
public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=150) private String title;
 @Column(nullable=false,length=1000) private String description;
 @Column(nullable=false,length=255) private String repositoryUrl;
 @Column(length=255) private String deployUrl;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="profile_id") private Profile profile;
 @ManyToMany @JoinTable(name="project_technologies",joinColumns=@JoinColumn(name="project_id"),inverseJoinColumns=@JoinColumn(name="technology_id")) private Set<Technology> technologies=new HashSet<>();
 public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;}
 public String getRepositoryUrl(){return repositoryUrl;} public String getDeployUrl(){return deployUrl;} public Profile getProfile(){return profile;} public Set<Technology> getTechnologies(){return technologies;}
 public void setTitle(String v){title=v;} public void setDescription(String v){description=v;} public void setRepositoryUrl(String v){repositoryUrl=v;} public void setDeployUrl(String v){deployUrl=v;} public void setProfile(Profile v){profile=v;} public void setTechnologies(Set<Technology> v){technologies=v;}
}
