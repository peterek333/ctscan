package pl.inz.ctscan.model.file;

import lombok.*;
import pl.inz.ctscan.model.base.ManualEntity;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.ect.EctData;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FileData extends ManualEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ectData_id")
    private ECTData ectData;

    @Enumerated(EnumType.STRING)
    FileType fileType;

    String originalFilename;

    String tempFilename;

    String dirPath;

    String fullPath;

    String suffix;
}
