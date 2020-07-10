package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.CreateProjectRequestDto;
import com.bsa.springdata.project.dto.ProjectDto;
import com.bsa.springdata.project.dto.ProjectSummaryDto;
import com.bsa.springdata.team.TeamRepository;
import com.bsa.springdata.team.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    EntityManager entityManager;

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

    public UUID createWithTeamAndTechnology(CreateProjectRequestDto createProjectRequest) {
        // TODO: Use common JPARepository methods. Build entities in memory and then persist them

        UUID uuid = UUID.randomUUID();

        var newProject = new Project();
        var teamResult = teamRepository.findByName(createProjectRequest.getTeamName());
        newProject.setName(createProjectRequest.getProjectName());
        newProject.setDescription(createProjectRequest.getProjectDescription());
        newProject.setTeams(teamResult.stream().collect(Collectors.toList()));
        newProject.setId(uuid);

        entityManager.persist(newProject);

        return uuid;
    }
}
