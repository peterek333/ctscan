package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.utils.FileConstants;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.MongoModelUtil;
import pl.inz.ctscan.db.file.FileDataRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;

import java.io.IOException;

//@Service
public class UploadService {
/*
    final
    FileManager fileManager;

    final
    FileDataRepository fileDataRepository;

    @Autowired
    public UploadService(FileManager fileManager, FileDataRepository fileDataRepository) {
        this.fileManager = fileManager;
        this.fileDataRepository = fileDataRepository;
    }

    public FileData uploadFile(MultipartFile file) throws IOException {
        FileType fileType = FileType.AIM;
        long suffix = fileDataRepository.countAllByFileTypeAndDirPath(fileType, FileConstants.FILE_AIM_PATH);

        String filePath = fileManager.concatFilePath(file.getOriginalFilename(), suffix, FileConstants.FILE_AIM_EXTENSION);

        fileManager.saveAimFile(file, filePath);

        FileData fileData = prepareFileData(file, filePath, fileType, "" + suffix);
        MongoModelUtil.setCreatedByIfNull(fileData);

        return fileDataRepository.save(fileData);
    }

    private FileData prepareFileData(MultipartFile file, String filePath, FileType fileType, String suffix) {
        return FileData.builder()
                .fileType(fileType)
                .originalFilename(file.getOriginalFilename())
                .tempFilename(FileManager.getFilenameFromFilePath(filePath))
                .dirPath(FileManager.getDirPathFromFilePath(filePath))
                .fullPath(filePath)
                .suffix(suffix)
                .build();
    }
    */
}
