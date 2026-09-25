package com.devshowcase.api.controller;

import com.devshowcase.api.dto.DTOs.*;
import com.devshowcase.api.service.*;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
class ProfileController {

    private final ProfileService s;

    ProfileController(ProfileService s) {
        this.s = s;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProfileResponse post(@Valid @RequestBody ProfileCreate d) {
        return s.create(d);
    }

    @GetMapping("/{id}")
    ProfileResponse get(@PathVariable Long id) {
        return s.get(id);
    }
}

@RestController
@RequestMapping("/api/technologies")
class TechnologyController {

    private final TechnologyService s;

    TechnologyController(TechnologyService s) {
        this.s = s;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TechnologyResponse post(@Valid @RequestBody TechnologyCreate d) {
        return s.create(d);
    }

    @GetMapping
    java.util.List<TechnologyResponse> get() {
        return s.all();
    }
}

@RestController
@RequestMapping("/api/projects")
class ProjectController {

    private final ProjectService s;

    ProjectController(ProjectService s) {
        this.s = s;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProjectResponse post(@Valid @RequestBody ProjectCreate d) {
        return s.create(d);
    }

    @GetMapping
    Page<ProjectResponse> get(
            @RequestParam(required = false) Long technologyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return s.search(technologyId, pageable);
    }

    @PostMapping("/{id}/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    FeedbackResponse feedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackCreate d
    ) {
        return s.addFeedback(id, d);
    }

    @PutMapping("/{id}/upvote")
    ProjectResponse upvote(@PathVariable Long id) {
        return s.upvote(id);
    }
}
