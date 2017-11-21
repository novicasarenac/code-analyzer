package codeAnalyzer.repository;

import codeAnalyzer.model.Component;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ComponentRepository extends Neo4jRepository<Component, Long> {

    List<Component> findByName(String name);

}
