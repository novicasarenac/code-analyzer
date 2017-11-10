package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codeAnalyzer.model.Method;
import codeAnalyzer.service.MethodService;

@Controller
public class MethodController {

	@Autowired
	private MethodService service;
	
	public Method create(Method method){
		return service.create(method);
	}
}
