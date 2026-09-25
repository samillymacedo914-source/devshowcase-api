package com.devshowcase.api.repository;

import com.devshowcase.api.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findDistinctByTechnologies_Id(
            Long technologyId,
            Pageable pageable
    );
}
