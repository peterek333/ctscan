package pl.inz.ctscan.db.relation;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.relation.RelFileDataExperiment;

public interface RelFileDataExperimentRepository extends MongoRepository<RelFileDataExperiment, String> {

}
