package pl.inz.ctscan.model.ect;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Frame extends FrameBase {

    @Column(length = 10000)
    private String data;

    @Column(length = 450)
    private String rowAverage;
}
