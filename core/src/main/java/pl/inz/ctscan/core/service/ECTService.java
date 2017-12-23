package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.MongoModelUtil;
import pl.inz.ctscan.db.ect.ECTExperimentRepository;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.db.ect.MeasurementRepository;
import pl.inz.ctscan.db.x.XExperimentRepository;
import pl.inz.ctscan.model.ect.ECTExperiment;
import pl.inz.ctscan.model.ect.Measurement;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.x.XExperiment;

@Service
public class ECTService {
//
//    private final MeasurementRepository measurementRepository;
//
//    private final FrameRepository frameRepository;

    final
    ECTExperimentRepository ectExperimentRepository;

    final
    XExperimentRepository xExperimentRepository;

//    final
//    FileManager fileManager;

    @Autowired
    public ECTService(
            //MeasurementRepository measurementRepository,
            ECTExperimentRepository ectExperimentRepository, XExperimentRepository xExperimentRepository) {
            //FileManager fileManager,
            //FrameRepository frameRepository) {
        //this.measurementRepository = measurementRepository;
        this.ectExperimentRepository = ectExperimentRepository;
        //this.fileManager = fileManager;
        //this.frameRepository = frameRepository;
        this.xExperimentRepository = xExperimentRepository;
    }

//    public Measurement getLastMeasurement() {
//        return measurementRepository.findTopByOrderByCreatedDateDesc();
//    }

    public ECTExperiment addExperiment(ECTExperiment ectExperiment) {
        return ectExperimentRepository.save(ectExperiment);
    }
    public XExperiment addExperiment(XExperiment ectExperiment) {
        return xExperimentRepository.save(ectExperiment);
    }
/*
    public boolean experimentExist(Long experimentId) {
        ECTExperiment ectExperiment = ectExperimentRepository.findOne(experimentId);

        return ectExperiment != null;
    }

    public Measurement addMeasurementFromFile(FileData fileData) {
        Measurement measurement = fileManager.readFileByJavaStream(fileData.getFullPath(), frameRepository);

        return measurementRepository.save(measurement);
    }
*/
}
