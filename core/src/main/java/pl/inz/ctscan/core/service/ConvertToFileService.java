package pl.inz.ctscan.core.service;

import org.springframework.stereotype.Service;
import pl.inz.ctscan.model.ect.PreparedFrame;
import pl.inz.ctscan.model.response.ProcessedECTFrame;
import pl.inz.ctscan.model.response.TopogramECTValue;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ConvertToFileService {

    public void sendTopogram(String topogramType, Long ectDataId, String dataFormat, Map<String, Object> result, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=" +
                prepareFilenameByFormat(topogramType, ectDataId, dataFormat));

        ServletOutputStream out = response.getOutputStream();
        if(dataFormat.equals("txt")) {
            sendTXTTopogram(out, result);
        } else {
            sendCSVTopogram(out, result);
        }

        out.flush();
        out.close();
    }

    public void sendPreparedFrame(String frameName, Long ectDataId, PreparedFrame frame, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=" +
                prepareFilenameByFormat(frameName, ectDataId, "txt"));

        ServletOutputStream out = response.getOutputStream();
        sendTXTPreparedFrame(out, frame);

        out.flush();
        out.close();

    }

    private void sendTXTPreparedFrame(ServletOutputStream out, PreparedFrame frame) {
        List<List<Float>> data = frame.getData();

        data.stream().forEach(row -> {
            row.stream().forEach(value -> {
                try {
                    out.print(value + " ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                out.print(";");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendCSVTopogram(ServletOutputStream out, Map<String, Object> result) throws IOException {
        List<ProcessedECTFrame> list = (List<ProcessedECTFrame>) result.get("content");

        out.println("X;Y");
        list.stream().forEach(e -> {
            try {
                List<TopogramECTValue> topogramValues = (List<TopogramECTValue>) e.getValues();
                StringBuilder values = new StringBuilder();
                for(TopogramECTValue v: topogramValues) {
                    values.append(v.getValue()).append(" ");
                }
                out.println(e.getMilliseconds() + ";" + values.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void sendTXTTopogram(ServletOutputStream out, Map<String, Object> result) throws IOException {
        List<Long> axisX = (List<Long>) result.get("X");
        List<List<TopogramECTValue>> axisY = (List<List<TopogramECTValue>>) result.get("Y");

        axisX.stream().forEach(x -> {
            try {
                out.print(x + " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.println();
        axisY.stream().forEach(y -> {
            try {
                StringBuilder values = new StringBuilder();
                for(TopogramECTValue v: y) {
                    values.append(v.getValue()).append(" ");
                }
                out.print(values.toString() + ";");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendGraph(String graphType, Long ectDataId, String dataFormat, Map<String, Object> result, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=" +
                prepareFilenameByFormat(graphType, ectDataId, dataFormat));

        ServletOutputStream out = response.getOutputStream();
        if(dataFormat.equals("txt")) {
            sendTXTGraph(out, result);
        } else {
            sendCSVGraph(out, result);
        }

        out.flush();
        out.close();
    }

    private void sendCSVGraph(ServletOutputStream out, Map<String, Object> result) throws IOException {
        List<ProcessedECTFrame> list = (List<ProcessedECTFrame>) result.get("content");

        out.println("X;Y");
        list.stream().forEach(e -> {
            try {
                out.println(e.getMilliseconds() + ";" + e.getValues());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void sendTXTGraph(ServletOutputStream out, Map<String, Object> result) throws IOException {
        List<Long> axisX = (List<Long>) result.get("X");
        List<Float> axisY = (List<Float>) result.get("Y");

        axisX.stream().forEach(x -> {
            try {
                out.print(x + " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.println();
        axisY.stream().forEach(y -> {
            try {
                out.print(y + " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String prepareFilenameByFormat(String suffix, Long ectDataId, String dataFormat) {
        return suffix + "_" + ectDataId + "_" + System.currentTimeMillis() + "." +
                (dataFormat.equals("txt") ? dataFormat : "csv");
    }
}
