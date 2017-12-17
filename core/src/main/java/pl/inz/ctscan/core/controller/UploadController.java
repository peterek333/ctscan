package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.RelationService;
import pl.inz.ctscan.core.service.UploadService;
import pl.inz.ctscan.core.utils.ResultProducer;
import pl.inz.ctscan.model.ect.Measurement;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.relation.RelFileDataExperiment;
import pl.inz.ctscan.model.relation.RelFileDataMeasurement;
import pl.inz.ctscan.model.relation.RelMeasurementExperiment;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    final
    UploadService uploadService;

    final
    RelationService relationService;

    final
    ECTService ectService;

    @Autowired
    public UploadController(UploadService uploadService, RelationService relationService, ECTService ectService) {
        this.uploadService = uploadService;
        this.relationService = relationService;
        this.ectService = ectService;
    }

    @PostMapping("/ect/aim")
    public Map<String, Object> uploadAimFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "id", required = false) String ectExperimentId) throws Exception {
        if (file.isEmpty()) {
            return ResultProducer.createResponse(false, "File is empty");
        }
        //Zapisanie pliku
        FileData fileData = uploadService.uploadFile(file);

        //Przetworzenie pliku na dane pomiarowe
        Measurement measurement = ectService.addMeasurementFromFile(fileData);

        //Nawiązanie relacji między plikiem, a danymi pomiarowymi
        RelFileDataMeasurement relFileDataMeasurement =
                relationService.addRelFileDataMeasurement(fileData.getId(), measurement.getId());

        //Nawiązanie relacji między eksperymentem, a dodanym plikiem oraz danymi pomiarowymi
        if(ectExperimentId != null) {
            RelFileDataExperiment relFileDataExperiment =
                    relationService.addRelFileDataExperimentIfExperimentExist(fileData, ectExperimentId);

            RelMeasurementExperiment relMeasurementExperiment =
                    relationService.addRelMeasurementExperiment(measurement.getId(), ectExperimentId);

            return ResultProducer.createResponseByReflection(fileData, relFileDataMeasurement, relFileDataExperiment, relMeasurementExperiment);
        }

        return ResultProducer.createResponseByReflection(fileData, relFileDataMeasurement,
                new Exception("Experiment doesn't exist but file upload successfully"));
    }
}
