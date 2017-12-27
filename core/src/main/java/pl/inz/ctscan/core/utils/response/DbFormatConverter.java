package pl.inz.ctscan.core.utils.response;

import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.PreparedFrame;

import java.util.ArrayList;
import java.util.List;

import static pl.inz.ctscan.core.utils.FileConstants.AIM_FRAME_SIZE_COL;
import static pl.inz.ctscan.core.utils.FileConstants.AIM_FRAME_SIZE_ROW;
import static pl.inz.ctscan.core.utils.FileConstants.CSV_SEPARATOR;

public class DbFormatConverter {

    public List<PreparedFrame> getPreparedFrames(List<Frame> frames) {
        List<PreparedFrame> preparedFrames = new ArrayList<>();
        for(Frame frame: frames) {
            PreparedFrame preparedFrame = processFrameToPreparedFrame(frame);

            preparedFrames.add(preparedFrame);
        }

        return preparedFrames;
    }

    private PreparedFrame processFrameToPreparedFrame(Frame frame) {
        List<List<Float>> data = new ArrayList<>();
        List<Float> rowAverage = new ArrayList<>();

        String[] frameData = frame.getData().split(CSV_SEPARATOR);
        String[] frameRowAverage = frame.getRowAverage().split(CSV_SEPARATOR);

        for(int i = 0; i < AIM_FRAME_SIZE_ROW; i++) {
            List<Float> rowData = new ArrayList<>();

            for(int j = 0; j < AIM_FRAME_SIZE_COL; j++) {
                int rowDataIndex = i*AIM_FRAME_SIZE_COL + j;

                rowData.add(Float.parseFloat(frameData[rowDataIndex]));
            }

            data.add(rowData);
            rowAverage.add(Float.parseFloat(frameRowAverage[i]));
        }

        return createPreparedFrame(frame, data, rowAverage);
    }

    private PreparedFrame createPreparedFrame(Frame frame, List<List<Float>> data, List<Float> rowAverage) {
        return PreparedFrame.builder()
                .id(frame.getId())
                .ectDataId(frame.getEctDataId())
                .number(frame.getNumber())
                .milliseconds(frame.getMilliseconds())
                .data(data)
                .rowAverage(rowAverage)
                .build();
    }
}
