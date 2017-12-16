package pl.inz.ctscan.db.ect;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.ect.ECTExperiment;

public interface ECTExperimentRepository extends MongoRepository<ECTExperiment, String> {

}
