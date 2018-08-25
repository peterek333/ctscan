package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.inz.ctscan.core.service.FileService;
import pl.inz.ctscan.model.file.FileData;

@RestController
@RequestMapping("/filedata")
public class FileDataController {

    private final FileService fileService;


    @Autowired
    public FileDataController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileDataId}")
    public FileData getFileData(@PathVariable Long fileDataId) {
        return fileService.getFileData(fileDataId);
    }
}
