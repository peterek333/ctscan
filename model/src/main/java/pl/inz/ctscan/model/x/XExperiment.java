package pl.inz.ctscan.model.x;


import lombok.*;
import pl.inz.ctscan.model.experiment.Experiment;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class XExperiment extends Experiment {

    private Long measurementId;
}
