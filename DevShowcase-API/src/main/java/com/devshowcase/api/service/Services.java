package com.devshowcase.api.service;

import com.devshowcase.api.dto.DTOs.*;
import com.devshowcase.api.entity.*;
import com.devshowcase.api.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class ProfileService {

    private final ProfileRepository r;

    ProfileService(ProfileRepository r) {
        this.r = r;
    }

    ProfileResponse create(ProfileCreate d) {
        Profile p = new Profile();
        p.setName(d.name().trim());
        p.setBio(d.bio().trim());
        p.setGithubUrl(d.githubUrl());
        p.setLinkedinUrl(d.linkedinUrl());
        return out(r.save(p));
    }

    @Transactional(readOnly = true)
    ProfileResponse get(Long id) {
        return out(r.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado: " + id)));
    }

    private ProfileResponse out(Profile p) {
        return new ProfileResponse(
                p.getId(),
                p.getName(),
                p.getBio(),
                p.getGithubUrl(),
                p.getLinkedinUrl(),
                p.getProjects().stream()
                        .map(x -> new ProjectSummary(x.getId(), x.getTitle()))
                        .toList()
        );
    }
}

@Service
class TechnologyService {

    private final TechnologyRepository r;

    TechnologyService(TechnologyRepository r) {
        this.r = r;
    }

    TechnologyResponse create(TechnologyCreate d) {
        Technology t = new Technology();
        t.setName(d.name().trim());
        return out(r.save(t));
    }

    List<TechnologyResponse> all() {
        return r.findAll().stream()
                .map(this::out)
                .toList();
    }

    private TechnologyResponse out(Technology t) {
        return new TechnologyResponse(t.getId(), t.getName());
    }
}

@Service
class ProjectService {

    private final ProjectRepository pr;
    private final ProfileRepository pf;
    private final TechnologyRepository tr;
    private final FeedbackRepository fr;

    ProjectService(
            ProjectRepository pr,
            ProfileRepository pf,
            TechnologyRepository tr,
            FeedbackRepository fr
    ) {
        this.pr = pr;
        this.pf = pf;
        this.tr = tr;
        this.fr = fr;
    }

    @Transactional
    ProjectResponse create(ProjectCreate d) {

        Profile p = pf.findById(d.profileId())
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Perfil não encontrado: " + d.profileId()
                        ));

        Project x = new Project();

        x.setTitle(d.title().trim());
        x.setDescription(d.description().trim());
        x.setRepositoryUrl(d.repositoryUrl());
        x.setDeployUrl(d.deployUrl());
        x.setProfile(p);

        if (d.technologyIds() != null) {
            for (Long id : d.technologyIds()) {
                x.getTechnologies().add(
                        tr.findById(id)
                                .orElseThrow(() ->
                                        new NoSuchElementException(
                                                "Tecnologia não encontrada: " + id
                                        ))
                );
            }
        }

        return out(pr.save(x));
    }

    @Transactional(readOnly = true)
    Page<ProjectResponse> search(Long technologyId, Pageable pageable) {

        if (technologyId == null) {
            return pr.findAll(pageable).map(this::out);
        }

        return pr.findDistinctByTechnologies_Id(technologyId, pageable)
                .map(this::out);
    }

    @Transactional
    FeedbackResponse addFeedback(Long projectId, FeedbackCreate d) {

        Project project = pr.findById(projectId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Projeto não encontrado: " + projectId
                        ));

        Feedback feedback = new Feedback();
        feedback.setAuthor(d.author().trim());
        feedback.setRating(d.rating());
        feedback.setComment(d.comment().trim());
        feedback.setProject(project);

        Feedback saved = fr.save(feedback);

        List<Feedback> feedbacks = fr.findByProjectId(projectId);

        double average = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        project.setAverageRating(average);
        pr.save(project);

        return new FeedbackResponse(
                saved.getId(),
                saved.getAuthor(),
                saved.getRating(),
                saved.getComment(),
                projectId
        );
    }

    @Transactional
    ProjectResponse upvote(Long projectId) {

        Project project = pr.findById(projectId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Projeto não encontrado: " + projectId
                        ));

        Integer current = project.getUpvotes();

        if (current == null) {
            current = 0;
        }

        project.setUpvotes(current + 1);

        return out(pr.save(project));
    }

    private ProjectResponse out(Project x) {

        return new ProjectResponse(
                x.getId(),
                x.getTitle(),
                x.getDescription(),
                x.getRepositoryUrl(),
                x.getDeployUrl(),
                x.getProfile().getId(),
                x.getProfile().getName(),
                x.getTechnologies().stream()
                        .map(t -> new TechnologyResponse(t.getId(), t.getName()))
                        .toList(),
                x.getAverageRating(),
                x.getUpvotes()
        );
    }
}
