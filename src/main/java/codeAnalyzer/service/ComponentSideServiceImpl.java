package codeAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.model.ComponentSide;
import codeAnalyzer.repository.ComponentSideRepository;

@Service
public class ComponentSideServiceImpl implements ComponentSideService {

	@Autowired
	private ComponentSideRepository repo;
	
	@Override
	public ComponentSide create(ComponentSide side) {
		return repo.save(side);
	}

}
