package codeAnalyzer.repository;

import codeAnalyzer.model.ComponentsRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ComponentsRelationshipRepository extends Neo4jRepository<ComponentsRelationship, Long> {
}
