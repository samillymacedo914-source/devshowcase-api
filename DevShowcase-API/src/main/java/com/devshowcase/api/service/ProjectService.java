package com.devshowcase.api.service;

import com.devshowcase.api.dto.DTOs.*;
import com.devshowcase.api.entity.Feedback;
import com.devshowcase.api.entity.Project;
import com.devshowcase.api.entity.Technology;
import com.devshowcase.api.repository.FeedbackRepository;
import com.devshowcase.api.repository.ProjectRepository;
import com.devshowcase.api.repository.TechnologyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProjectService {

    private final ProjectRepository projects;
    private final TechnologyRepository technologies;
    private final FeedbackRepository feedbacks;

    public ProjectService(
            ProjectRepository projects,
            TechnologyRepository technologies,
            FeedbackRepository feedbacks) {
        this.projects = projects;
        this.technologies = technologies;
        this.feedbacks = feedbacks;
    }

    public ProjectResponse create(ProjectCreate d) {
        Project p = new Project();
        p.setTitle(d.title().trim());
        p.setDescription(d.description().trim());
        p.setRepositoryUrl(d.repositoryUrl().trim());
        p.setDeployUrl(d.deployUrl());

        return out(projects.save(p));
    }

    public Page<ProjectResponse> search(Long technologyId, Pageable pageable) {
        Page<Project> page;

        if (technologyId == null) {
            page = projects.findAll(pageable);
        } else {
            page = projects.findDistinctByTechnologies_Id(
                    technologyId, pageable);
        }

        return page.map(this::out);
    }

    public FeedbackResponse addFeedback(Long id, FeedbackCreate d) {
        Project p = projects.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Projeto não encontrado: " + id));

        Feedback f = new Feedback();
        f.setAuthor(d.author().trim());
        f.setRating(d.rating());
        f.setComment(d.comment().trim());
        f.setProject(p);

        Feedback saved = feedbacks.save(f);

        var list = feedbacks.findByProjectId(id);

        double average = list.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        p.setAverageRating(average);
        projects.save(p);

        return new FeedbackResponse(
                saved.getId(),
                saved.getAuthor(),
                saved.getRating(),
                saved.getComment()
        );
    }

    public ProjectResponse upvote(Long id) {
        Project p = projects.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Projeto não encontrado: " + id));

        p.setUpvotes(p.getUpvotes() + 1);

        return out(projects.save(p));
    }

    private ProjectResponse out(Project x) {
        return new ProjectResponse(
                x.getId(),
                x.getTitle(),
                x.getDescription(),
                x.getRepositoryUrl(),
                x.getDeployUrl(),
                x.getAverageRating(),
                x.getUpvotes()
        );
    }
}
