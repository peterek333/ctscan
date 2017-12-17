package pl.inz.ctscan.model.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.AbstractEntity;

@Getter
@Setter
@Builder
public class FileData extends AbstractEntity {

    FileType fileType;

    String originalFilename;

    String tempFilename;

    String dirPath;

    String fullPath;

    String suffix;
}
