package pl.inz.ctscan.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.inz.ctscan.model.ect.utils.Pixel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopogramECTValue {
    private Float value;
    private Pixel pixel;
}
