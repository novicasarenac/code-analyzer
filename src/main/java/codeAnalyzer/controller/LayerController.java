package codeAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codeAnalyzer.model.Layer;
import codeAnalyzer.service.LayerService;

@Controller
public class LayerController {
	@Autowired
	private LayerService service;
	
	public Layer create(Layer layer){
		return service.create(layer);
	}
}
