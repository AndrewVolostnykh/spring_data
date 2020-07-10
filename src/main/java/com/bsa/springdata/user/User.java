package com.bsa.springdata.user;

import com.bsa.springdata.office.Office;
import com.bsa.springdata.role.Role;
import com.bsa.springdata.team.Team;
import com.bsa.springdata.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Map table users to this entity

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String firstName;

    private String lastName;

    private int experience;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "User2Role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public static User fromDto(CreateUserDto user, Office office, Team team) {
        return User.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .experience(user.getExperience())
            .office(office)
            .team(team)
            .build();
    }
}
