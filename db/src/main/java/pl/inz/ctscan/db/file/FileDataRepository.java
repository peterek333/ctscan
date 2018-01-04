package pl.inz.ctscan.db.file;


import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;

public interface FileDataRepository extends CrudRepository<FileData, Long> {

    long countAllByFileTypeAndDirPath(FileType fileType, String dirPath);
    FileData findByTempFilename(String tempFilename);
    FileData findByEctData_ExperimentId(Long expId);

}
