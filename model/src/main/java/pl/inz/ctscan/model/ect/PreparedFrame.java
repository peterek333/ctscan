package pl.inz.ctscan.model.ect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreparedFrame extends FrameBase {

    List<List<Float>> data;

    List<Float> rowAverage;

    @Builder
    public PreparedFrame(List<List<Float>> data, List<Float> rowAverage, Long id, Long ectDataId, long number, long milliseconds) {
        super(id, ectDataId, number, milliseconds);
        this.data = data;
        this.rowAverage = rowAverage;
    }
}
