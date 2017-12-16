package pl.inz.ctscan.model.ect;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.inz.ctscan.model.Experiment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ectExperiment")
public class ECTExperiment extends Experiment {

    private String measurementId;
}
