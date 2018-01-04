package pl.inz.ctscan.model.ect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.TableManualEntity;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FileData;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ECTData extends TableManualEntity {

    private Long experimentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ectData")
    private FileData fileData;

    private DataStatus status;

    private Integer electrodes;
    private Integer measurements;

    private Integer plane;
}
