package codeAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.model.Layer;
import codeAnalyzer.repository.LayerRepository;

@Service
public class LayerServiceImpl implements LayerService {
	
	@Autowired
	private LayerRepository repo;
	
	@Override
	public Layer create(Layer layer) {
		return repo.save(layer);
	}

}
