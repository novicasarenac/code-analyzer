package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codeAnalyzer.model.Line;
import codeAnalyzer.service.LineService;

@Controller
public class LineController {

	@Autowired
	private LineService service;
	
	public Line create(Line line){
		return service.create(line);
	}
}
