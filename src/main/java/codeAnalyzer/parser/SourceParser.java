package codeAnalyzer.parser;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.controller.ClassController;
import codeAnalyzer.controller.ComponentSideController;
import codeAnalyzer.controller.LayerController;
import codeAnalyzer.controller.LineController;
import codeAnalyzer.controller.MethodController;
import codeAnalyzer.controller.ProjectController;
import codeAnalyzer.controller.SectionController;
import codeAnalyzer.model.Classs;
import codeAnalyzer.model.ComponentSide;
import codeAnalyzer.model.Layer;
import codeAnalyzer.model.Line;
import codeAnalyzer.model.Method;
import codeAnalyzer.model.Project;
import codeAnalyzer.model.Section;

@Service
public class SourceParser {

	@Autowired
	private ProjectController projC;
	@Autowired
	private ComponentSideController sideC;
	@Autowired
	private LayerController layerC;
	@Autowired
	private ClassController classC;
	@Autowired
	private MethodController methodC;
	@Autowired
	private SectionController sectionC;
	@Autowired
	private LineController lineC;
	
	
    @PostConstruct
    public void startParsing() throws IOException {
        /*String route = "code/setup.json";
        File homeDir = new File(System.getProperty("user.home"));
        String setup = new Scanner(new File(homeDir, route)).useDelimiter("\\Z").next();

        ObjectMapper mapper = new ObjectMapper();
        final MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        final Map<String, Object> data = mapper.readValue(setup, type);*/
    	
    	//System.out.println(SourceParser.class.getSimpleName());
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
    	lineC.create(line);
    }
}
