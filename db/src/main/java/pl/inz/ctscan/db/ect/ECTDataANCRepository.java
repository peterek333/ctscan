package pl.inz.ctscan.db.ect;

import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.ect.ECTDataANC;
import pl.inz.ctscan.model.ect.EctData;
import pl.inz.ctscan.model.file.FileType;

public interface ECTDataANCRepository extends CrudRepository<EctData, Long> {
    public EctData findByFileData_FileType(FileType fileType);
}
