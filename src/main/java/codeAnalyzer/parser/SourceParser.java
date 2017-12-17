package codeAnalyzer.parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentType;
import codeAnalyzer.model.ComponentsRelationship;
import codeAnalyzer.repository.ComponentPartRepository;
import codeAnalyzer.repository.ComponentRepository;
import codeAnalyzer.repository.ComponentsRelationshipRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SourceParser {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentsRelationshipRepository componentsRelationshipRepository;

    @Autowired
    private ComponentPartRepository componentPartRepository;

	@Autowired
	private PackageParser packageParser;

    @PostConstruct
    public void startParsing() throws IOException {
        String route = "/Documents/codeAnalyzer/setup.json";
        String sourceRoute = "/Documents/codeAnalyzer/code-analyzer/src";
        File homeDir = new File(System.getProperty("user.home"));
        String setup = new Scanner(new File(homeDir, route)).useDelimiter("\\Z").next();

        ObjectMapper mapper = new ObjectMapper();
        final MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        final Map<String, Object> data = mapper.readValue(setup, type);

        packageParser.parsePackages(sourceRoute);

        Component component1 = new Component(ComponentType.CLASS, "User1");
        componentRepository.save(component1);

        Component component2 = new Component(ComponentType.CLASS, "User2");
        componentRepository.save(component2);

        ComponentsRelationship componentsRelationship = new ComponentsRelationship(2, component1, component2);
        componentsRelationshipRepository.save(componentsRelationship);
    }
}
