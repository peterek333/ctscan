package pl.inz.ctscan.model.experiment;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.AutoEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
//@Entity
public class Keyword extends AutoEntity {

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experiment_id")
    private Experiment experiment;

}
