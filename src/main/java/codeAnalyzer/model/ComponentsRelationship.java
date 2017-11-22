package codeAnalyzer.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LINKED")
public class ComponentsRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private int weight;

    @StartNode
    private Component startNode;

    @EndNode
    private Component endNode;

    public ComponentsRelationship() {
    }

    public ComponentsRelationship(Long id, int weight, Component startNode, Component endNode) {
        this.id = id;
        this.weight = weight;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Component getStartNode() {
        return startNode;
    }

    public void setStartNode(Component startNode) {
        this.startNode = startNode;
    }

    public Component getEndNode() {
        return endNode;
    }

    public void setEndNode(Component endNode) {
        this.endNode = endNode;
    }
}
