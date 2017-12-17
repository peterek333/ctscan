package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.MongoModelUtil;
import pl.inz.ctscan.db.ect.ECTExperimentRepository;
import pl.inz.ctscan.db.ect.MeasurementRepository;
import pl.inz.ctscan.model.ect.ECTExperiment;
import pl.inz.ctscan.model.ect.Measurement;
import pl.inz.ctscan.model.file.FileData;

@Service
public class ECTService {

    private final MeasurementRepository measurementRepository;

    final
    ECTExperimentRepository ectExperimentRepository;

    final
    FileManager fileManager;

    @Autowired
    public ECTService(MeasurementRepository measurementRepository, ECTExperimentRepository ectExperimentRepository, FileManager fileManager) {
        this.measurementRepository = measurementRepository;
        this.ectExperimentRepository = ectExperimentRepository;
        this.fileManager = fileManager;
    }

    public Measurement getLastMeasurement() {
        return measurementRepository.findTopByOrderByCreatedDateDesc();
    }

    public ECTExperiment addExperiment(ECTExperiment ectExperiment) {
        MongoModelUtil.setCreatedByIfNull(ectExperiment);

        return ectExperimentRepository.save(ectExperiment);
    }

    public boolean experimentExist(String experimentId) {
        ECTExperiment ectExperiment = ectExperimentRepository.findOne(experimentId);

        return ectExperiment != null;
    }

    public Measurement addMeasurementFromFile(FileData fileData) {
        Measurement measurement = fileManager.readFileByJavaStream(fileData.getFullPath());

        return measurementRepository.save(measurement);
    }
}
