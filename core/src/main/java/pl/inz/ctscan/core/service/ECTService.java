package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.db.ect.ECTExperimentRepository;
import pl.inz.ctscan.db.ect.MeasurementRepository;
import pl.inz.ctscan.model.ect.ECTExperiment;
import pl.inz.ctscan.model.ect.Measurement;

@Service
public class ECTService {

    private final MeasurementRepository measurementRepository;

    final
    ECTExperimentRepository ectExperimentRepository;

    @Autowired
    public ECTService(MeasurementRepository measurementRepository, ECTExperimentRepository ectExperimentRepository) {
        this.measurementRepository = measurementRepository;
        this.ectExperimentRepository = ectExperimentRepository;
    }

    public Measurement getLastMeasurement() {
        return measurementRepository.findTopByOrderByCreatedDateDesc();
    }

    public ECTExperiment addExperiment(ECTExperiment ectExperiment) {
        return ectExperimentRepository.save(ectExperiment);
    }
}
