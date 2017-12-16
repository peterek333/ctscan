package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.service.UploadService;
import pl.inz.ctscan.core.utils.ResultProducer;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;

    @PostMapping("/ect/aim")
    public Map<String, Object> uploadAimFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "id", required = false) String ectExperimentId) throws IOException {
        if (file.isEmpty()) {
            return ResultProducer.createResponse(false, "File is empty");
        }
        uploadService.uploadFile(file);
        return ResultProducer.createResponse(true);
    }
}
