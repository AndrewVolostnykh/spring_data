package com.bsa.springdata.team;

import com.bsa.springdata.project.Project;
import com.bsa.springdata.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

// TODO: Map table teams to this entity
@Data
@Entity
@Table(name = "teams")
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String room;
    private String area;
    @ManyToMany
    @JoinColumn(name = "id")
    private Project project;
    @OneToMany(orphanRemoval = true, mappedBy = "teams")
    private List<User> users;
    @ManyToOne
    @JoinColumn(name = "id")
    private Technology technology;
}
