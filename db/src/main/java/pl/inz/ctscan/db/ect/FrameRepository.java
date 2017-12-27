package pl.inz.ctscan.db.ect;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.ect.Frame;

import java.util.List;

public interface FrameRepository extends CrudRepository<Frame, Long> {

    List<Frame> getFramesByEctDataId(Long ectDataId);
    Page<Frame> getFramesByEctDataId(Long ectDataId, Pageable pageRequest);
}
