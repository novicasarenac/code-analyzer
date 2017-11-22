package codeAnalyzer.repository;

import codeAnalyzer.model.ComponentPart;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ComponentPartRepository extends Neo4jRepository<ComponentPart, Long> {
}
