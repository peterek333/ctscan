package pl.inz.ctscan.db.ect;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.file.FileType;

public interface ECTDataRepository extends JpaRepository<ECTData, Long> {

    Page<ECTData> findByCreatedBy(String username, Pageable pageRequest);
}
