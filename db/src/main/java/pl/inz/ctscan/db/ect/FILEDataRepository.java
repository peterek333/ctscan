package pl.inz.ctscan.db.ect;

import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.file.FILEData;

public interface FILEDataRepository extends CrudRepository<FILEData, Long> {
    public FILEData findByEctData_ExperimentId(Long expId);
}
