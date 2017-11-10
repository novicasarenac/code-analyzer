package codeAnalyzer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import codeAnalyzer.model.Classs;

public interface ClassRepository extends PagingAndSortingRepository<Classs, Long> {

}
