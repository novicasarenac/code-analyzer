package codeAnalyzer.model;

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

    public Component() {
    }

    public Component(ComponentType componentType, String name) {
        this.componentType = componentType;
        this.name = name;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
