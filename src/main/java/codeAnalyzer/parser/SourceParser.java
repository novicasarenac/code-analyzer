package codeAnalyzer.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SourceParser {

	@Autowired
	private PackageParser packageParser;

	@Autowired
    private JSONParser jsonParser;

    @PostConstruct
    public void startParsing() throws IOException {
        String sourceRoute = "/Downloads/students-no-security/src";
        String jsonFilePath = "/Documents/codeAnalyzer/res";
        File homeDir = new File(System.getProperty("user.home"));
        String jsonFile = new Scanner(new File(homeDir, jsonFilePath)).useDelimiter("\\Z").next();
        packageParser.parsePackages(sourceRoute);
        JSONObject json = new JSONObject(jsonFile);
        jsonParser.ParseJSON(json);

    }
}
