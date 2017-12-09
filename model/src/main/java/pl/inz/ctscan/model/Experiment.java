package pl.inz.ctscan.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.inz.ctscan.model.base.AbstractEntity;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Experiment extends AbstractEntity {


    private String title;

    private String keywords;

    private Date date;

    private String measurementId;
}
