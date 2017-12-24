package pl.inz.ctscan.model.ect;

import lombok.*;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private long number;

    @NonNull
    private long milliseconds;

    //private float

    //private List<List<Float>> data;

    @Column(length = 10000)
    private String data;
}
