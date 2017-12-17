package pl.inz.ctscan.db.file;


import org.springframework.data.mongodb.repository.MongoRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;

public interface FileDataRepository extends MongoRepository<FileData, String> {

    long countAllByFileTypeAndDirPath(FileType fileType, String dirPath);

}
