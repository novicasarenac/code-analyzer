package codeAnalyzer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import codeAnalyzer.model.Line;

public interface LineRepository extends PagingAndSortingRepository<Line, Long> {

}
