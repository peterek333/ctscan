package pl.inz.ctscan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.inz.ctscan.model.base.AbstractEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class Experiment extends AbstractEntity {


    private String title;

    private List<String> keywords;

    private long finishedTimestamp;
}
