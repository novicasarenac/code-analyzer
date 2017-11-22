package codeAnalyzer.parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentType;
import codeAnalyzer.parser.javaParser.PackageParser;
import codeAnalyzer.repository.ComponentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SourceParser {

    @Autowired
    private ComponentRepository componentRepository;

	@Autowired
	private PackageParser packageParser;

    @PostConstruct
    public void startParsing() throws IOException {
        String route = "code/setup.json";
        String sourceRoute = "/code/test/code-analyzer/src";
        File homeDir = new File(System.getProperty("user.home"));
        String setup = new Scanner(new File(homeDir, route)).useDelimiter("\\Z").next();

        ObjectMapper mapper = new ObjectMapper();
        final MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        final Map<String, Object> data = mapper.readValue(setup, type);

        packageParser.parsePackages(sourceRoute);
        Component component = new Component(ComponentType.CLASS, "User");
        componentRepository.save(component);

        Component comp = componentRepository.findByName("User").get(0);
        System.out.println("Name: " + comp.getName() + " Type: " + comp.getComponentType());
    }
}
