package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ectDataId;

    @NonNull
    private long number;

    @NonNull
    private long milliseconds;

    @Column(length = 10000)
    private String data;

    @Column(length = 450)
    private String rowAverage;
}
