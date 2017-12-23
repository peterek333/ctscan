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
@MappedSuperclass
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Experiment extends ManualEntity {

    private String title;

//    @OneToMany(fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL)
//    private List<Keyword> keywords;

    @ElementCollection
    private Set<String> keywords = new HashSet<>();

    private long finishedTimestamp;
}
