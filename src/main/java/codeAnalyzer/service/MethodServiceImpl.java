package codeAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.model.Method;
import codeAnalyzer.repository.MethodRepository;

@Service
public class MethodServiceImpl implements MethodService {
	@Autowired
	private MethodRepository repo;

	@Override
	public Method create(Method method) {
		return repo.save(method);
	}
	
	
}
