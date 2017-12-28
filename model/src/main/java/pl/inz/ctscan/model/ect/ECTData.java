package pl.inz.ctscan.model.ect;

import lombok.*;
import pl.inz.ctscan.model.base.ManualEntity;
import pl.inz.ctscan.model.file.DataStatus;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "data_ect")
public class ECTData extends ManualEntity {

    private Long experimentId;

    private Long fileDataId;

    private String dataAverage;

    private DataStatus status;
}
