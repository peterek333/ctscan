package pl.inz.ctscan.model.ect;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Frame {

    @NonNull
    private long number;

    @NonNull
    private long milliseconds;

    //private float

    private List<List<Float>> data;
}
