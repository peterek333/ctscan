package pl.inz.ctscan.core.utils.response;

import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.PreparedFrame;
import pl.inz.ctscan.model.file.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.inz.ctscan.core.utils.FileConstants.*;

public class DbFormatConverter {

    public List<PreparedFrame> getPreparedFrames(List<Frame> frames, ECTData ectData, FileType fileType) {
        return frames.parallelStream()
                .map(f -> processFrameToPreparedFrame(f, ectData, fileType))
                .collect(Collectors.toList());
    }

    public PreparedFrame processFrameToPreparedFrame(Frame frame, ECTData ectData, FileType fileType) {
        List<List<Float>> data = new ArrayList<>();

        String[] frameData = frame.getData().split(CSV_SEPARATOR);

        preprocessData(data, frameData, ectData, fileType);

        return createPreparedFrame(frame, data, frame.getFrameAverage());
    }

    private void preprocessData(List<List<Float>> data, String[] frameData, ECTData ectData, FileType fileType) {
        if(fileType == FileType.AIM) {
            for(int i = 0; i < AIM_FRAME_SIZE_ROW; i++) {
                List<Float> rowData = new ArrayList<>();

                for(int j = 0; j < AIM_FRAME_SIZE_COL; j++) {
                    int rowDataIndex = i*AIM_FRAME_SIZE_COL + j;

                    rowData.add(Float.parseFloat(frameData[rowDataIndex]));
                }

                data.add(rowData);
            }
        } else {
            int maxMeasurementsInLine = (ectData.getElectrodes() - 1);
            int calculatedValues = 0;
            while(maxMeasurementsInLine >= 1) {
                List<Float> rowData = new ArrayList<>();

                int j;
                for(j = 0; j < maxMeasurementsInLine; j++) {
                    int rowDataIndex = calculatedValues + j;

                    rowData.add(Float.parseFloat(frameData[rowDataIndex]));
                }

                data.add(rowData);
                calculatedValues += j;
                maxMeasurementsInLine--;
            }
        }
    }

    private PreparedFrame createPreparedFrame(Frame frame, List<List<Float>> data, Float frameAverage) {
        return PreparedFrame.builder()
                .id(frame.getId())
                .ectDataId(frame.getEctDataId())
                .number(frame.getNumber())
                .milliseconds(frame.getMilliseconds())
                .data(data)
                .frameAverage(frameAverage)
                .build();
    }
}
