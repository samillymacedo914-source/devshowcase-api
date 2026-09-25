package com.devshowcase.api.service;
import com.devshowcase.api.dto.DTOs.*; import com.devshowcase.api.entity.*; import com.devshowcase.api.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;

@Service
class ProfileService {
 private final ProfileRepository r; ProfileService(ProfileRepository r){this.r=r;}
 ProfileResponse create(ProfileCreate d){Profile p=new Profile();p.setName(d.name().trim());p.setBio(d.bio().trim());p.setGithubUrl(d.githubUrl());p.setLinkedinUrl(d.linkedinUrl());return out(r.save(p));}
 @Transactional(readOnly=true) ProfileResponse get(Long id){return out(r.findById(id).orElseThrow(()->new NoSuchElementException("Perfil não encontrado: "+id)));}
 private ProfileResponse out(Profile p){return new ProfileResponse(p.getId(),p.getName(),p.getBio(),p.getGithubUrl(),p.getLinkedinUrl(),p.getProjects().stream().map(x->new ProjectSummary(x.getId(),x.getTitle())).toList());}
}
@Service
class TechnologyService {
 private final TechnologyRepository r; TechnologyService(TechnologyRepository r){this.r=r;}
 TechnologyResponse create(TechnologyCreate d){Technology t=new Technology();t.setName(d.name().trim());return out(r.save(t));}
 List<TechnologyResponse> all(){return r.findAll().stream().map(this::out).toList();}
 private TechnologyResponse out(Technology t){return new TechnologyResponse(t.getId(),t.getName());}
}
@Service
class ProjectService {
 private final ProjectRepository pr; private final ProfileRepository pf; private final TechnologyRepository tr;
 ProjectService(ProjectRepository pr,ProfileRepository pf,TechnologyRepository tr){this.pr=pr;this.pf=pf;this.tr=tr;}
 @Transactional ProjectResponse create(ProjectCreate d){
  Profile p=pf.findById(d.profileId()).orElseThrow(()->new NoSuchElementException("Perfil não encontrado: "+d.profileId()));
  Project x=new Project();x.setTitle(d.title().trim());x.setDescription(d.description().trim());x.setRepositoryUrl(d.repositoryUrl());x.setDeployUrl(d.deployUrl());x.setProfile(p);
  if(d.technologyIds()!=null) for(Long id:d.technologyIds()) x.getTechnologies().add(tr.findById(id).orElseThrow(()->new NoSuchElementException("Tecnologia não encontrada: "+id)));
  return out(pr.save(x));
 }
 @Transactional(readOnly=true) List<ProjectResponse> all(){return pr.findAll().stream().map(this::out).toList();}
 private ProjectResponse out(Project x){return new ProjectResponse(x.getId(),x.getTitle(),x.getDescription(),x.getRepositoryUrl(),x.getDeployUrl(),x.getProfile().getId(),x.getProfile().getName(),x.getTechnologies().stream().map(t->new TechnologyResponse(t.getId(),t.getName())).toList());}
}
