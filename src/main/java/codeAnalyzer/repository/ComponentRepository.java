package codeAnalyzer.repository;

import codeAnalyzer.model.Component;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ComponentRepository extends Neo4jRepository<Component, Long> {

    List<Component> findByName(String name);

    @Query("match (n) detach delete n")
    void deleteAll();

}
