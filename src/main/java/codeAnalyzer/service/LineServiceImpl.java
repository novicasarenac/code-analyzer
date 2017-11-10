package codeAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.model.Line;
import codeAnalyzer.repository.LineRepository;

@Service
public class LineServiceImpl implements LineService {

	@Autowired
	private LineRepository repo;

	@Override
	public Line create(Line line) {
		return repo.save(line);
	}
	
	
}
