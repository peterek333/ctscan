package pl.inz.ctscan.db.ect;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.ect.Measurement;

public interface MeasurementRepository extends MongoRepository<Measurement, String> {

    Measurement findById(String id);
    Measurement findTopByOrderByCreatedDateDesc();
}
