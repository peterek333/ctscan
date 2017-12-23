package pl.inz.ctscan.model.ect;

import lombok.*;
import pl.inz.ctscan.model.base.ManualEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Frame extends ManualEntity {

    @NonNull
    private long number;

    @NonNull
    private long milliseconds;

    //private float

    private List<List<Float>> data;
}
