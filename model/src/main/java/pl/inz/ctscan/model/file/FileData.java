package pl.inz.ctscan.model.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Builder
@Entity
public class FileData extends ManualEntity {

    @Enumerated(EnumType.STRING)
    FileType fileType;

    String originalFilename;

    String tempFilename;

    String dirPath;

    String fullPath;

    String suffix;
}
