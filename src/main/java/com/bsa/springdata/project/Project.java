package com.bsa.springdata.project;

import com.bsa.springdata.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

// TODO: Map table projects to this entity
@Data
@Entity
@Table(name = "projects")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Team> teams;
}
