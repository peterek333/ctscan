package pl.inz.ctscan.model.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;

@Getter
@Setter
@Builder
public class FileData extends ManualEntity {

    FileType fileType;

    String originalFilename;

    String tempFilename;

    String dirPath;

    String fullPath;

    String suffix;
}
