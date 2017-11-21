package codeAnalyzer.parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import codeAnalyzer.parser.javaParser.PackageParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SourceParser {

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

    	/*System.out.println(SourceParser.class.getSimpleName());
    	Project project = new Project("aaaaa");
    	projC.create(project);
    	ComponentSide side = new ComponentSide("server", project);
    	sideC.create(side);
    	Layer layer = new Layer("controller", side);
    	layerC.create(layer);
    	Classs classs = new Classs("Class","",layer);
    	classC.create(classs);
    	Method method = new Method("getName", "String", "", classs);
    	methodC.create(method);
    	Section section = new Section("", 10, 20, method);
    	sectionC.create(section);
    	Line line = new Line("", 16, section);
    	lineC.create(line);*/
    }
}
