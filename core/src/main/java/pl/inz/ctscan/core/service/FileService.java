package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.db.file.FileDataRepository;
import pl.inz.ctscan.model.file.FileData;

@Service
public class FileService {

    private final FileDataRepository fileDataRepository;

    @Autowired
    public FileService(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    public FileData getFileData(Long fileDataId) {
        return fileDataRepository.findOne(fileDataId);
    }

}
