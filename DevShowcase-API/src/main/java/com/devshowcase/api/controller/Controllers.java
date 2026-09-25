package com.devshowcase.api.controller;
import com.devshowcase.api.dto.DTOs.*; import com.devshowcase.api.service.*; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.*;

@RestController @RequestMapping("/api/profiles")
class ProfileController { private final ProfileService s; ProfileController(ProfileService s){this.s=s;} @PostMapping @ResponseStatus(HttpStatus.CREATED) ProfileResponse post(@Valid @RequestBody ProfileCreate d){return s.create(d);} @GetMapping("/{id}") ProfileResponse get(@PathVariable Long id){return s.get(id);} }
@RestController @RequestMapping("/api/technologies")
class TechnologyController { private final TechnologyService s; TechnologyController(TechnologyService s){this.s=s;} @PostMapping @ResponseStatus(HttpStatus.CREATED) TechnologyResponse post(@Valid @RequestBody TechnologyCreate d){return s.create(d);} @GetMapping List<TechnologyResponse> get(){return s.all();} }
@RestController @RequestMapping("/api/projects")
class ProjectController { private final ProjectService s; ProjectController(ProjectService s){this.s=s;} @PostMapping @ResponseStatus(HttpStatus.CREATED) ProjectResponse post(@Valid @RequestBody ProjectCreate d){return s.create(d);} @GetMapping List<ProjectResponse> get(){return s.all();} }
