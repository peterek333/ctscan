package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.UploadService;
import pl.inz.ctscan.core.utils.ResultProducer;
import pl.inz.ctscan.model.ect.Measurement;
import pl.inz.ctscan.model.file.FileData;

import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {
/*
    final
    UploadService uploadService;

    final
    ECTService ectService;

    @Autowired
    public UploadController(UploadService uploadService, ECTService ectService) {
        this.uploadService = uploadService;
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
        //Measurement measurement = ectService.addMeasurementFromFile(fileData);

        //Nawiązanie relacji między eksperymentem, a dodanym plikiem oraz danymi pomiarowymi
        if(ectExperimentId != null) {

            return ResultProducer.createResponseByReflection(fileData);
        }

        return ResultProducer.createResponseByReflection(fileData,
                new Exception("Experiment doesn't exist but file upload successfully"));
    }
    */
}
