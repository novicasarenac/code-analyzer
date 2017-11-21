package codeAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("codeAnalyzer.model")
public class CodeAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeAnalyzerApplication.class, args);
	}
}
