package pl.inz.ctscan.model.file;

import lombok.*;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
