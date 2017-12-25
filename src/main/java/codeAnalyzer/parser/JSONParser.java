package codeAnalyzer.parser;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentsRelationship;
import codeAnalyzer.model.SectionComponent;
import codeAnalyzer.repository.ComponentRepository;
import codeAnalyzer.repository.ComponentsRelationshipRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JSONParser {


    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentsRelationshipRepository componentsRelationshipRepository;

    public void ParseJSON(JSONObject obj){

        ArrayList<SectionComponent> noGood = new ArrayList<SectionComponent>();

        JSONArray sections = (JSONArray) obj.get("file");
        List list = sections.toList();
        for(int i=0; i<list.size(); i++){
            HashMap object = (HashMap) list.get(i);
            String fileName = (String)object.get("fileName");
            Integer lineNumEnd = (Integer)object.get("lineNumEnd");
            Integer lineNumStart = (Integer)object.get("lineNumStart");
            String comment = (String)object.get("comment");
            String content = (String)object.get("content");
            String type = (String)object.get("sectionType");

            String [] comments = comment.split(";");
            Component component = null;
            String componentName = "";
            SectionComponent section = null;
            if(comments.length==2){
                componentName = fileName + "." + comments[0];
                String sectionName = fileName + "." + comments[0];
                section = new SectionComponent(sectionName, lineNumStart, lineNumEnd, content, type);

            }else if(comments.length==3 && comments[2].equals("annotation")){
                if(fileName.equals(comments[1]))
                    componentName = fileName;
                else
                    componentName = fileName + "." + comments[1];
                String annotationName = fileName + "." + comments[0];
                section = new SectionComponent(annotationName, lineNumStart, lineNumEnd, content, type);
            }else if(comments.length==3){
                componentName = fileName + "." + comments[0];
                String subComponentName = fileName + "." + comments[1];
                section = new SectionComponent(subComponentName, lineNumStart, lineNumEnd, content, type);
            }

            try {
                component = componentRepository.findByName(componentName).get(0);
                componentRepository.save(section);
                ComponentsRelationship relationship = new ComponentsRelationship(1, component, section);
                componentsRelationshipRepository.save(relationship);
            }catch (Exception e){
                System.out.println(componentName + " NE POSTOJI TAKVA KOMPONENTA");
                noGood.add(section);
            }
        }

        System.out.println("Check");
    }
}
