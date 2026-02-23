package com.example.demo.models;

import jakarta.persistence.*;

/**
 * Base entity for people involved in film production (e.g. Directors, Actors).
 * Uses JOINED inheritance: subclasses have their own tables with a PK that
 * references this table.
 */
@Entity(name = "Artists")
@Table(name = "artists")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected Artist() {
    }

    protected Artist(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
