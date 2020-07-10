package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.CreateProjectRequestDto;
import com.bsa.springdata.project.dto.ProjectDto;
import com.bsa.springdata.project.dto.ProjectSummaryDto;
import com.bsa.springdata.team.TeamRepository;
import com.bsa.springdata.team.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private TeamRepository teamRepository;

    public List<ProjectDto> findTop5ByTechnology(String technology) {
        // TODO: Use single query to load data. Sort by number of developers in a project
        //  Hint: in order to limit the query you can either use native query with limit or Pageable interface

        Pageable pageable = PageRequest.of(0, 5);

        ;

        return projectRepository.findByTechnology(technology, pageable)
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDto> findTheBiggest() {
        // TODO: Use single query to load data. Sort by teams, developers, project name
        //  Hint: in order to limit the query you can either use native query with limit or Pageable interface
        return null;
    }

    public List<ProjectSummaryDto> getSummary() {
        // TODO: Try to use native query and projection first. If it fails try to make as few queries as possible
        return null;
    }

    public int getCountWithRole(String role) {
        // TODO: Use a single query
        return 0;
    }

    public UUID createWithTeamAndTechnology(CreateProjectRequestDto createProjectRequest) {
        // TODO: Use common JPARepository methods. Build entities in memory and then persist them
        return null;
    }
}
