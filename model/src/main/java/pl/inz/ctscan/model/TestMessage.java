package pl.inz.ctscan.model;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TestMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topSecret;
}
