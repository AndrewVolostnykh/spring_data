package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.CreateProjectRequestDto;
import com.bsa.springdata.project.dto.ProjectDto;
import com.bsa.springdata.project.dto.ProjectSummaryDto;
import com.bsa.springdata.team.Team;
import com.bsa.springdata.team.TeamRepository;
import com.bsa.springdata.team.Technology;
import com.bsa.springdata.team.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(0, 5);

        return projectRepository.findByTechnology(technology, pageable)
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDto> findTheBiggest() {

        return Optional.of(ProjectDto.fromEntity(projectRepository.findTheBiggest().get(0)));
    }

    public List<ProjectSummaryDto> getSummary() {
        // TODO: Try to use native query and projection first. If it fails try to make as few queries as possible
        return null;
    }

    public int getCountWithRole(String role) {
        return projectRepository.countByUsersRoles(role);
    }

    public UUID createWithTeamAndTechnology(CreateProjectRequestDto req) {
        // TODO: Use common JPARepository methods. Build entities in memory and then persist them

        var technology = Technology
                .builder()
                .description(req.getTechDescription())
                .link(req.getTechLink())
                .name(req.getTech())
                .build();


        var team = Team
                .builder()
                .name(req.getTeamName())
                .area(req.getTeamArea())
                .room(req.getTeamRoom())
                .technology(technology)
                .build();


        var project = Project
                .builder()
                .name(req.getProjectName())
                .description(req.getProjectDescription())
                .teams(List.of(team))
                .build();


        return projectRepository.save(project).getId();
    }
}
