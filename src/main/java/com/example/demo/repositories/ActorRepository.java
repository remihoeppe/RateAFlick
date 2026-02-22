package com.example.demo.repositories;

import com.example.demo.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByName(String name);

    List<Actor> findByNameIn(List<String> names);
}
