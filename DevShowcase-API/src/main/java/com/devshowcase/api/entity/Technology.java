package com.devshowcase.api.entity;
import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="technologies")
public class Technology {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String name;
 @ManyToMany(mappedBy="technologies") private Set<Project> projects=new HashSet<>();
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
}
