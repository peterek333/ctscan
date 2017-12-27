package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.UploadService;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.file.FileData;

@RestController
@RequestMapping("/upload")
public class UploadController {

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
    public ECTData uploadAimFile(@RequestParam("file") MultipartFile file,
                                 @RequestParam(value = "id", required = false) Long experimentId,
                                 @RequestParam(value = "process", required = false) boolean processNow) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }
        //Zapisanie pliku
        FileData fileData = uploadService.uploadFile(file);

        //Relacja między plikiem do przetworzenia, a eksperymentu(o ile podano)
        ECTData ectData = ectService.createECTData(fileData, experimentId);

        if(processNow) {
            //Przetworzenie pliku na dane pomiarowe
            ectService.addFramesFromFile(fileData.getFullPath(), ectData);
        }

        return ectData;
    }

}
