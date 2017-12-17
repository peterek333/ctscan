package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.MongoModelUtil;
import pl.inz.ctscan.core.utils.ResultProducer;
import pl.inz.ctscan.db.relation.RelFileDataExperimentRepository;
import pl.inz.ctscan.db.relation.RelFileDataMeasurementRepository;
import pl.inz.ctscan.db.relation.RelMeasurementExperimentRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.relation.RelFileDataExperiment;
import pl.inz.ctscan.model.relation.RelFileDataMeasurement;
import pl.inz.ctscan.model.relation.RelMeasurementExperiment;

@Service
public class RelationService {

    private final
    RelFileDataExperimentRepository relFileDataExperimentRepository;

    private final RelMeasurementExperimentRepository relMeasurementExperimentRepository;

    private final RelFileDataMeasurementRepository relFileDataMeasurementRepository;

    private final
    ECTService ectService;

    @Autowired
    public RelationService(RelFileDataExperimentRepository relFileDataExperimentRepository, ECTService ectService, RelMeasurementExperimentRepository relMeasurementExperimentRepository, RelFileDataMeasurementRepository relFileDataMeasurementRepository) throws Exception {
        this.relFileDataExperimentRepository = relFileDataExperimentRepository;
        this.ectService = ectService;
        this.relMeasurementExperimentRepository = relMeasurementExperimentRepository;
        this.relFileDataMeasurementRepository = relFileDataMeasurementRepository;
    }

    public RelFileDataExperiment addRelFileDataExperiment(String fileDataId, String experimentId) {
        RelFileDataExperiment relFileDataExperiment = new RelFileDataExperiment(fileDataId, experimentId);
        MongoModelUtil.setCreatedByIfNull(relFileDataExperiment);

        return relFileDataExperimentRepository.save(relFileDataExperiment);
    }

    public RelFileDataExperiment addRelFileDataExperimentIfExperimentExist(FileData fileData, String experimentId) throws Exception {
        boolean experimentExist = ectService.experimentExist(experimentId);

        if(experimentExist) {
            return addRelFileDataExperiment(fileData.getId(), experimentId);
        }

        return null;
    }

    public RelMeasurementExperiment addRelMeasurementExperiment(String measurementId, String experimentId) {
        RelMeasurementExperiment relMeasurementExperiment = new RelMeasurementExperiment();
        relMeasurementExperiment.setMeasurementId(measurementId);
        relMeasurementExperiment.setExperimentId(experimentId);

        return relMeasurementExperimentRepository.save(relMeasurementExperiment);
    }

    public RelFileDataMeasurement addRelFileDataMeasurement(String fileDataId, String measurementId) {
        RelFileDataMeasurement relFileDataMeasurement = new RelFileDataMeasurement();
        relFileDataMeasurement.setFileDataId(fileDataId);
        relFileDataMeasurement.setMeasurementId(measurementId);

        return relFileDataMeasurementRepository.save(relFileDataMeasurement);
    }

}
