package pl.inz.ctscan.db.experiment;

import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.experiment.Experiment;

import java.util.List;

public interface ExperimentRepository extends CrudRepository<Experiment, Long> {

    List<Experiment> findAll();
    List<Experiment> findAllByCreatedBy(String createdBy);
}
