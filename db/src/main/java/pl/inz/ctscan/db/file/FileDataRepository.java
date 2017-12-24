package pl.inz.ctscan.db.file;


import org.springframework.data.repository.CrudRepository;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;

public interface FileDataRepository extends CrudRepository<FileData, Long> {

    long countAllByFileTypeAndDirPath(FileType fileType, String dirPath);

}
