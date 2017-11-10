package codeAnalyzer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import codeAnalyzer.model.Layer;

public interface LayerRepository extends PagingAndSortingRepository<Layer, Long> {

}
