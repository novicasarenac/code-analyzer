package codeAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.model.Classs;
import codeAnalyzer.repository.ClassRepository;

@Service
public class ClassServiceImpl implements ClassService {
	
	@Autowired
	private ClassRepository repo;

	@Override
	public Classs create(Classs classs) {
		return repo.save(classs);
	}
}
