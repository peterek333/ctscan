package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.utils.FileConstants;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.db.file.FileDataRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;

import java.io.IOException;
import java.util.UUID;

@Service
public class UploadService {

    final FileManager fileManager;

    final FileDataRepository fileDataRepository;


    @Value("${file.generate.uuid}")
    private boolean enableUUID;

    @Autowired
    public UploadService(FileManager fileManager, FileDataRepository fileDataRepository) {
        this.fileManager = fileManager;
        this.fileDataRepository = fileDataRepository;
    }

    public FileData uploadFile(MultipartFile file) throws Exception {
        FileType fileType = FileType.AIM;
        String suffix = "";
        String filePath;

        if(enableUUID) {
            String filename = generateFilenameByUUID(fileType);

            filePath = fileManager.concatFilePath(filename, FileConstants.FILE_AIM_EXTENSION);
        } else {
            long numSuffix = fileDataRepository.countAllByFileTypeAndDirPath(fileType, FileConstants.FILE_AIM_PATH);
            suffix += numSuffix;

            filePath = fileManager.concatFilePath(file.getOriginalFilename(), numSuffix, FileConstants.FILE_AIM_EXTENSION);
        }
        fileManager.saveAimFile(file, filePath);

        FileData fileData = prepareFileData(file, filePath, fileType, suffix);

        return fileDataRepository.save(fileData);
    }

    private String generateFilenameByUUID(FileType fileType) throws Exception {
        int infinityLoopGuard = 0;
        int MAX_LOOPS = 100;
        boolean isInDatabase;

        String filename;

        do {
            if(infinityLoopGuard > MAX_LOOPS) {
                throw new Exception("Too much loops for generating UUID");
            }
            infinityLoopGuard++;

            UUID uuid = UUID.randomUUID();
            filename = uuid.toString() + "." + fileType;

            isInDatabase = fileDataRepository.findByTempFilename(filename) != null;
        } while(isInDatabase);

        return filename;
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

}
