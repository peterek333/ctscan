package pl.inz.ctscan.model.relation;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.inz.ctscan.model.base.AbstractEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelFileDataExperiment extends AbstractEntity {

    String fileDataId;

    String experimentId;

}
