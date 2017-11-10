package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codeAnalyzer.model.Section;
import codeAnalyzer.service.SectionService;

@Controller
public class SectionController {

	@Autowired
	private SectionService service;
	
	public Section create(Section section){
		return service.create(section);
	}
}
