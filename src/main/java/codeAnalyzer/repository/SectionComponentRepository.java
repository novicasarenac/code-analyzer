package codeAnalyzer.repository;

import codeAnalyzer.model.SectionComponent;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface SectionComponentRepository extends Neo4jRepository<SectionComponent, Long> {

    @Query("match (sectionAnswer:SectionComponent)<-[:LINKED*]-(answerFile:Component)," +
            " (trueSection:SectionComponent)<-[:LINKED*]-(trueFile:Component), " +
            "path=shortestPath((sectionAnswer)-[distanceWeight:LINKED*]-(trueSection)) " +
            "where answerFile.name<>{answerFile} and trueFile.name<>{trueFile} " +
            "and sectionAnswer.startLine<{answerLine} and sectionAnswer.endLine>{answerLine}" +
            " and trueSection.startLine<{trueLine} and trueSection.endLine>{trueLine} " +
            "with distinct relationships(path) as rels return reduce(sumw = 0, x in rels|sumw + x.weight)")
    int findShortestPath(@Param("answerLine") int answerLine, @Param("answerFile") String answerFile,
                               @Param("trueLine") int trueLine, @Param("trueFile") String trueFile);
}
