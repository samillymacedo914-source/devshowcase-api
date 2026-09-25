package com.devshowcase.api.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.*;

public final class DTOs {

    private DTOs() {}

    public record ProfileCreate(
            @NotBlank @Size(max = 120) String name,
            @NotBlank @Size(max = 500) String bio,
            @URL String githubUrl,
            @URL String linkedinUrl
    ) {}

    public record ProfileResponse(
            Long id,
            String name,
            String bio,
            String githubUrl,
            String linkedinUrl,
            List<ProjectSummary> projects
    ) {}

    public record ProjectSummary(
            Long id,
            String title
    ) {}

    public record TechnologyCreate(
            @NotBlank @Size(max = 80) String name
    ) {}

    public record TechnologyResponse(
            Long id,
            String name
    ) {}

    public record ProjectCreate(
            @NotBlank @Size(max = 150) String title,
            @NotBlank @Size(max = 1000) String description,
            @NotBlank @URL String repositoryUrl,
            @URL String deployUrl,
            @NotNull Long profileId,
            Set<Long> technologyIds
    ) {}

    public record ProjectResponse(
            Long id,
            String title,
            String description,
            String repositoryUrl,
            String deployUrl,
            Long profileId,
            String profileName,
            List<TechnologyResponse> technologies,
            Double averageRating,
            Integer upvotes
    ) {}

    public record FeedbackCreate(
            @NotBlank @Size(max = 120) String author,
            @NotNull @Min(1) @Max(5) Integer rating,
            @NotBlank @Size(max = 1000) String comment
    ) {}

    public record FeedbackResponse(
            Long id,
            String author,
            Integer rating,
            String comment,
            Long projectId
    ) {}
}
