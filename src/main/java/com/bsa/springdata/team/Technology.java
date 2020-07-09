package com.bsa.springdata.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

// TODO: Map table technologies to this entity
@Data
@Entity
@Table(name = "technologies")
@NoArgsConstructor
@AllArgsConstructor
public class Technology {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String description;
    private String link;
}