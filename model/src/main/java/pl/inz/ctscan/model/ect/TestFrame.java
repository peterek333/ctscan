package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class TestFrame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private long number;

    @NonNull
    private long milliseconds;

//    @OneToMany(mappedBy = "frame")
//    private List<TestFrameRow> rows;


}
