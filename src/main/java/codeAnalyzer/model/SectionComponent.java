package codeAnalyzer.model;


import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class SectionComponent extends Component {

    private int startLine;

    private int endLine;

    public SectionComponent(String name, int startLine, int endLine){
        this.componentType = ComponentType.SECTION;
        this.name = name;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }
}
