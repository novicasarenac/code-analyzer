package codeAnalyzer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import codeAnalyzer.model.Method;

public interface MethodRepository extends PagingAndSortingRepository<Method, Long> {

}
