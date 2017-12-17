package pl.inz.ctscan.db.ect;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.ect.Frame;

public interface FrameRepository extends MongoRepository<Frame, String> {
}
