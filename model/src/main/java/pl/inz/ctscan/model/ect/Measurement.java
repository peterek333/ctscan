package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Measurement extends ManualEntity {

    private List<String> framesId;

    public Measurement() {
        framesId = new ArrayList<>();
    }
}
