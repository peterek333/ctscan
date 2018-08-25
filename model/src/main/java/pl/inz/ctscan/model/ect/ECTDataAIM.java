package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "data_ect_aim")
public class ECTDataAIM extends ECTData {

    private Integer pixels;
}
