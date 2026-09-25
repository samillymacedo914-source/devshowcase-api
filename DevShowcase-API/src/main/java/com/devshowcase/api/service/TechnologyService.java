package com.devshowcase.api.service;

import com.devshowcase.api.dto.DTOs.*;
import com.devshowcase.api.entity.Technology;
import com.devshowcase.api.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyRepository r;

    public TechnologyService(TechnologyRepository r) {
        this.r = r;
    }

    public TechnologyResponse create(TechnologyCreate d) {
        Technology t = new Technology();
        t.setName(d.name().trim());
        return out(r.save(t));
    }

    public List<TechnologyResponse> all() {
        return r.findAll().stream()
                .map(this::out)
                .toList();
    }

    private TechnologyResponse out(Technology x) {
        return new TechnologyResponse(
                x.getId(),
                x.getName()
        );
    }
}
