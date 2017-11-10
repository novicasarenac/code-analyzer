package codeAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codeAnalyzer.model.Section;
import codeAnalyzer.repository.SectionRepository;

@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private SectionRepository repo;
	
	@Override
	public Section create(Section section) {
		return repo.save(section);
	}

}
