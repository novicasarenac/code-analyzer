package codeAnalyzer.model;

import codeAnalyzer.ComponentType;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Component {

    @Id
    @GeneratedValue
    private Long id;

    private ComponentType componentType;

    private String name;

}
