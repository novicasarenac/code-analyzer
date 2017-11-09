package codeAnalyzer.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

@Service
public class SourceParser {

    @PostConstruct
    public void startParsing() throws IOException {
        String route = "code/setup.json";
        File homeDir = new File(System.getProperty("user.home"));
        String setup = new Scanner(new File(homeDir, route)).useDelimiter("\\Z").next();

        ObjectMapper mapper = new ObjectMapper();
        final MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        final Map<String, Object> data = mapper.readValue(setup, type);
    }
}
