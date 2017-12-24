package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class TestFrameRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//
//    @ManyToOne
//    private TestFrame frame;

    @ElementCollection
    private List<Float> values = new ArrayList<>();

}
