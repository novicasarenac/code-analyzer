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
import org.json.JSONObject;
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

	@Autowired
    private JSONParser jsonParser;

    @PostConstruct
    public void startParsing() throws IOException {
        String route = "/code/setup.json";
        String sourceRoute = "/Code/code-analyzer/src";
        File homeDir = new File(System.getProperty("user.home"));
        String setup = new Scanner(new File(homeDir, route)).useDelimiter("\\Z").next();

        ObjectMapper mapper = new ObjectMapper();
        final MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        final Map<String, Object> data = mapper.readValue(setup, type);

        packageParser.parsePackages(sourceRoute);


        String jsonString = "{\"package\": \"model\", \"class\": \"Component\", \"method\" : \"getName\"," +
                        " \"section\" : { \"name\" : \"if\", \"startLine\" : \"5\", \"endLine\" : \"7\"} }";
        String jsonString2 = "{\"package\": \"model\", \"class\": \"Component\", \"method\" : \"setName\"," +
                " \"section\" : { \"name\" : \"if_else\", \"startLine\" : \"5\", \"endLine\" : \"7\"} }";


        JSONObject jsonObj = new JSONObject(jsonString);
        jsonParser.ParseJSON(jsonObj);
        jsonObj = new JSONObject(jsonString2);
        jsonParser.ParseJSON(jsonObj);

    }
}
