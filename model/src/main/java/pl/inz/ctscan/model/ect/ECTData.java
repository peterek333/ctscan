package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "data_ect")
public class ECTData extends ManualEntity {

    private Long experimentId;

    private Long fileDataId;
}
