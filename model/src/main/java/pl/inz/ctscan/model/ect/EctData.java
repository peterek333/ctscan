package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;
import pl.inz.ctscan.model.base.TableManualEntity;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FILEData;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EctData extends TableManualEntity {

    private Long experimentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ectData")
    private FILEData fileData;


    private DataStatus status;

    private Integer electrodes;
    private Integer measurements;
}
