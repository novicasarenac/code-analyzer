package codeAnalyzer.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import codeAnalyzer.repository.ComponentRepository;
import codeAnalyzer.repository.SectionComponentRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SourceParser {

	@Autowired
	private PackageParser packageParser;

	@Autowired
    private JSONParser jsonParser;

	@Autowired
	private SectionComponentRepository sectionComponentRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @PostConstruct
    public void startParsing() throws IOException {
        String sourceRoute = "/Downloads/students-no-security/src";
        String jsonFilePath = "/Documents/codeAnalyzer/res";
        File homeDir = new File(System.getProperty("user.home"));
        String jsonFile = new Scanner(new File(homeDir, jsonFilePath)).useDelimiter("\\Z").next();

        componentRepository.deleteAll();
        packageParser.parsePackages(sourceRoute);
        JSONObject json = new JSONObject(jsonFile);
        jsonParser.ParseJSON(json);

        String answerFile = "ExamController.createExam";
        String trueFile = "ExamController.createExam";
        int answerLine = 60;
        int trueLine = 40;

        try {
            int distance = sectionComponentRepository.findShortestPath(answerLine, answerFile, trueLine, trueFile);
            System.out.println(distance);
        }catch(Exception e){
            System.out.println("Bad file name or line number");
        }

    }
}
