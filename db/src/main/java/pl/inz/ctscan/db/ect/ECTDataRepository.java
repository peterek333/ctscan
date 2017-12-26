package pl.inz.ctscan.db.ect;

import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.ect.ECTData;

public interface ECTDataRepository extends CrudRepository<ECTData, Long> {

}
