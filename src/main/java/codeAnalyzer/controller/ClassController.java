package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codeAnalyzer.model.Classs;
import codeAnalyzer.service.ClassService;

@Controller
public class ClassController {
	
	@Autowired
	private ClassService service;
	
	public Classs create(Classs classs){
		return service.create(classs);
	}
}
