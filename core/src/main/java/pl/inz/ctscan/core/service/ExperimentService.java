package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.db.experiment.ExperimentRepository;
import pl.inz.ctscan.model.experiment.Experiment;

import java.util.List;

@Service
public class ExperimentService {

    private final ExperimentRepository experimentRepository;

    @Autowired
    public ExperimentService(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    public Experiment addExperiment(Experiment experiment) {
        return experimentRepository.save(experiment);
    }

    public Experiment getExperiment(Long id) {
        return experimentRepository.findOne(id);
    }

    public List<Experiment> getListExperiment() {
        return experimentRepository.findAll();
    }

    public List<Experiment> getListExperimentCreatedBy(String createdBy) {
        return experimentRepository.findAllByCreatedBy(createdBy);
    }
}
