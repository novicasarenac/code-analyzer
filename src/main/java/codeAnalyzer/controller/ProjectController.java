package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codeAnalyzer.model.Project;
import codeAnalyzer.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService service;
	
	public Project create(Project project){
		return service.create(project);
	}
}
