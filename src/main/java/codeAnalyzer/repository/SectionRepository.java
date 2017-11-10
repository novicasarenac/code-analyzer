package codeAnalyzer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import codeAnalyzer.model.Section;

public interface SectionRepository extends PagingAndSortingRepository<Section, Long>{

}
