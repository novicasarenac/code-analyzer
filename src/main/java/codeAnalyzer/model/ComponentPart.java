package codeAnalyzer.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ComponentPart {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public ComponentPart(String name) {
        this.name = name;
    }

    public ComponentPart() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
