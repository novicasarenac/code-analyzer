package codeAnalyzer.model;


import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class SectionComponent extends Component {

    private int startLine;
    private int endLine;
    private String content;
    private String type;

    public SectionComponent(String name, int startLine, int endLine, String content, String type){
        this.componentType = ComponentType.SECTION;
        this.name = name;
        this.startLine = startLine;
        this.endLine = endLine;
        this.content = content;
        this.type = type;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
