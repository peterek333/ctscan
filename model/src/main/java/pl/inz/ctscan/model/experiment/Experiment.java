package pl.inz.ctscan.model.experiment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Experiment extends ManualEntity {

    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords = new HashSet<>();

    private long finishedTimestamp;
}
