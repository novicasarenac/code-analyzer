package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import codeAnalyzer.model.ComponentSide;
import codeAnalyzer.service.ComponentSideService;

@Controller
public class ComponentSideController {

	@Autowired
	private ComponentSideService service;
	
	public ComponentSide create(ComponentSide side){
		return service.create(side);
	}
}
