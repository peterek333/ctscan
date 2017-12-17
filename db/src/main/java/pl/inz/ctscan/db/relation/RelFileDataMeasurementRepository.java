package pl.inz.ctscan.db.relation;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.relation.RelFileDataMeasurement;


public interface RelFileDataMeasurementRepository extends MongoRepository<RelFileDataMeasurement, String> {
}
