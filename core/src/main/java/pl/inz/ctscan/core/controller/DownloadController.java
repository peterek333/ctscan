package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.inz.ctscan.core.service.DownloadService;
import pl.inz.ctscan.core.service.FileService;
import pl.inz.ctscan.model.file.FileData;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity getFile(@PathVariable Long fileId) throws Exception {
        FileData fileData = fileService.getFileData(fileId);

        byte[] fileBytes = downloadService.getFileBytes(fileData);

        return createFileResponse(fileData, fileBytes);
    }

    private ResponseEntity createFileResponse(FileData fileData, byte[] fileBytes) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileData.getOriginalFilename());
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        return new ResponseEntity(fileBytes, responseHeaders, HttpStatus.OK);
    }


}
