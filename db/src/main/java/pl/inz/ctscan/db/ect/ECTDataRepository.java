package pl.inz.ctscan.db.ect;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.file.FileType;

public interface ECTDataRepository extends CrudRepository<ECTData, Long> {

    ECTData findByFileData_FileType(FileType fileType);
    Page<ECTData> findByCreatedBy(String username, Pageable pageRequest);
}
