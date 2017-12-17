package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.MongoModelUtil;
import pl.inz.ctscan.core.utils.ResultProducer;
import pl.inz.ctscan.db.relation.RelFileDataExperimentRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.relation.RelFileDataExperiment;

@Service
public class RelationService {

    final
    RelFileDataExperimentRepository relFileDataExperimentRepository;

    final
    ECTService ectService;

    @Autowired
    public RelationService(RelFileDataExperimentRepository relFileDataExperimentRepository, ECTService ectService) throws Exception {
        this.relFileDataExperimentRepository = relFileDataExperimentRepository;
        this.ectService = ectService;
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

}
