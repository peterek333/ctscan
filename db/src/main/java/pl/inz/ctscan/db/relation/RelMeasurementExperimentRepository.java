package pl.inz.ctscan.db.relation;


import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.relation.RelMeasurementExperiment;

public interface RelMeasurementExperimentRepository extends MongoRepository<RelMeasurementExperiment, String> {


}
