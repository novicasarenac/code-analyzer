package codeAnalyzer.parser;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentsRelationship;
import codeAnalyzer.model.SectionComponent;
import codeAnalyzer.repository.ComponentRepository;
import codeAnalyzer.repository.ComponentsRelationshipRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JSONParser {


    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentsRelationshipRepository componentsRelationshipRepository;

    public void ParseJSON(JSONObject obj){

        JSONObject sectionJSON = (JSONObject) obj.get("section");
        String name = (String)sectionJSON.get("name");
        Integer startLine = Integer.parseInt((String)sectionJSON.get("startLine"));
        Integer endLine = Integer.parseInt((String)sectionJSON.get("endLine"));
        SectionComponent section = new SectionComponent(name, startLine, endLine);
        componentRepository.save(section);

        String methodName = (String)obj.get("method");
        Component method = componentRepository.findByName(methodName).get(0);

        ComponentsRelationship relationship = new ComponentsRelationship(1, method, section);
        componentsRelationshipRepository.save(relationship);
        System.out.println("nestooo");
    }
}
