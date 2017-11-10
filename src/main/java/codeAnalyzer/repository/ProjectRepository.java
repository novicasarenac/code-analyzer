package codeAnalyzer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import codeAnalyzer.model.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

}
