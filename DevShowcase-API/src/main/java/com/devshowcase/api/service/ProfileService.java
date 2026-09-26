package com.devshowcase.api.service;

import com.devshowcase.api.dto.DTOs.*;
import com.devshowcase.api.entity.Profile;
import com.devshowcase.api.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ProfileRepository r;

    public ProfileService(ProfileRepository r) {
        this.r = r;
    }

    public ProfileResponse create(ProfileCreate d) {
        Profile p = new Profile();

        p.setName(d.name().trim());
        p.setBio(d.bio().trim());
        p.setGithubUrl(d.githubUrl());
        p.setLinkedinUrl(d.linkedinUrl());

        return out(r.save(p));
    }

    public ProfileResponse get(Long id) {
        return out(r.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Perfil não encontrado: " + id)));
    }

    private ProfileResponse out(Profile x) {
        return new ProfileResponse(
                x.getId(),
                x.getName(),
                x.getBio(),
                x.getGithubUrl(),
                x.getLinkedinUrl(),
                x.getProjects().stream()
                        .map(p -> new ProjectSummary(
                                p.getId(),
                                p.getTitle()))
                        .collect(Collectors.toList())
        );
    }
}
