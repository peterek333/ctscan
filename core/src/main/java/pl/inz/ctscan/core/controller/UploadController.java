package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.UploadService;
import pl.inz.ctscan.core.service.queue.QueueService;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;

@RestController
@RequestMapping("/upload")
public class UploadController {

    final UploadService uploadService;

    final ECTService ectService;

    final QueueService queueService;

    @Autowired
    public UploadController(UploadService uploadService, ECTService ectService, QueueService queueService) {
        this.uploadService = uploadService;
        this.ectService = ectService;
        this.queueService = queueService;
    }

    @PostMapping("/ect/{fileType}")
    public ECTData uploadAimFile(@PathVariable FileType fileType,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam(value = "id", required = false) Long experimentId,
                                 @RequestParam(value = "process", required = false) boolean processNow) throws Exception {

        return uploadFile(fileType, file, experimentId, processNow);
    }

    private ECTData uploadFile(FileType fileType, MultipartFile file, Long experimentId, boolean processNow) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }
        //Zapisanie pliku
        FileData fileData = uploadService.uploadFile(file, fileType);

        //Relacja między plikiem do przetworzenia, a eksperymentu(o ile podano)
        ECTData ectData = ectService.createECTData(fileData, experimentId);

        if(processNow) {
            //Przetworzenie pliku na dane pomiarowe
            queueService.processFrames(ectData.getId());
        }

        return ectData;
    }

}
