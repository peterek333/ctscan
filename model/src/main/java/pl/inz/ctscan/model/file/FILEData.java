package pl.inz.ctscan.model.file;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;
import pl.inz.ctscan.model.ect.EctData;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "filed")
public class FILEData extends ManualEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ectData_id")
    private EctData ectData;

    @Enumerated(EnumType.STRING)
    FileType fileType;

    String originalFilename;

    String tempFilename;

    String dirPath;

    String fullPath;

    String suffix;
}