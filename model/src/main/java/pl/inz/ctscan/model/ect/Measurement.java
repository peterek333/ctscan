package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.inz.ctscan.model.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Measurement extends AbstractEntity {

//    private long sizeX;
//    private long sizeY;

    private List<Frame> frames;


    public Measurement() {
        frames = new ArrayList<>();
    }
}
