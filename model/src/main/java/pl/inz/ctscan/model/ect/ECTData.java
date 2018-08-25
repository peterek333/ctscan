package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.TableManualEntity;
import pl.inz.ctscan.model.file.DataStatus;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ECTData extends TableManualEntity {

    private Long experimentId;

    private Long fileDataId;

    private DataStatus status;

    private Integer electrodes;
    private Integer measurements;

    private Integer plane;
}
