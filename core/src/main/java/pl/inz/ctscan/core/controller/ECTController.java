package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ConvertToFileService;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.queue.QueueService;
import pl.inz.ctscan.model.QueryOptions;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.PreparedFrame;
import pl.inz.ctscan.model.ect.utils.Pixel;
import pl.inz.ctscan.model.response.PreparedPage;
import pl.inz.ctscan.model.response.ProcessedECTFrame;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ect")
public class ECTController {

    private final ECTService ectService;

    private final QueueService queueService;

    private final ConvertToFileService convertToFileService;

    @Autowired
    public ECTController(ECTService ectService, QueueService queueService, ConvertToFileService convertToFileService) {
        this.ectService = ectService;
        this.queueService = queueService;
        this.convertToFileService = convertToFileService;
    }

    @GetMapping("/process/{ectDataId}")
    public boolean startProcessFrames(@PathVariable Long ectDataId) throws Exception {
        return queueService.processFrames(ectDataId);
    }

    @PostMapping("/data/user/{username}")
    public Page<ECTData> getECTData(@PathVariable String username,
                                    @RequestBody QueryOptions queryOptions) {
        return ectService.getECTDataByUsername(username, queryOptions);
    }

    @GetMapping("/data/{ectDataId}")
    public ECTData getECTData(@PathVariable Long ectDataId) {
        return ectService.getECTData(ectDataId);
    }

    @GetMapping("/frame/{ectDataId}")
    public PreparedFrame getFrames(@PathVariable Long ectDataId,
                                   @RequestParam Long frameNumber) {

        Frame frame = ectService.getFrame(ectDataId, frameNumber);
        ECTData ectData = ectService.getECTData(ectDataId);

        return ectService.preparedFrameFromFrame(frame, ectData);
    }
    @GetMapping("/frames/{ectDataId}")
    public List<PreparedFrame> getFrames(@PathVariable Long ectDataId) {

        List<Frame> frames = ectService.getFrames(ectDataId);
        ECTData ectData = ectService.getECTData(ectDataId);

        return ectService.preparedFramesFromFrames(frames, ectData);
    }

    @PostMapping("/frames/{ectDataId}")
    public PreparedPage<PreparedFrame> getFramesByPage(@PathVariable Long ectDataId,
                                                       @RequestBody QueryOptions queryOptions) {
        Page<Frame> frames = ectService.getFramesByPage(ectDataId, queryOptions);
        ECTData ectData = ectService.getECTData(ectDataId);

        return ectService.preparePageFromFrames(frames, ectData);
    }

    @PostMapping("/aim/graph/{ectDataId}")
    public Map<String, Object> getAimGraph(@PathVariable Long ectDataId,
                                           @RequestParam(required = false) Boolean separateInArray,
                                           @RequestBody Pixel pixel) {
        Map<String, Object> result = new HashMap<>();
        if(separateInArray != null && separateInArray) {
            result = ectService.getAimGraphInArray(ectDataId, pixel);
        } else {
            List<ProcessedECTFrame> list = ectService.getAimGraph(ectDataId, pixel);
            result.put("content", list);
        }
        return result;
    }

    @PostMapping("/download/aim/graph/{ectDataId}")
    public void getAimGraphFile(@PathVariable Long ectDataId,
                                @RequestParam String dataFormat,
                                @RequestBody Pixel pixel,
                                HttpServletResponse response) throws IOException {

        Boolean separateInArray = dataFormat.equals("txt");
        Map<String, Object> result = getAimGraph(ectDataId, separateInArray, pixel);

        convertToFileService.sendGraph("graphAim", ectDataId, dataFormat, result, response);
    }


    @PostMapping("/aim/topogram/{ectDataId}")
    public Map<String, Object> getAimTopogram(@PathVariable Long ectDataId,
                                              @RequestParam(required = false) Boolean separateInArray,
                                              @RequestBody List<Pixel> pixels) {
        Map<String, Object> result = new HashMap<>();
        if(separateInArray != null && separateInArray) {
            result = ectService.getAimTopogramInArray(ectDataId, pixels);
        } else {
            List<ProcessedECTFrame> list = ectService.getAimTopogram(ectDataId, pixels);
            result.put("content", list);
        }
        return result;
    }

    @PostMapping("/download/aim/topogram/{ectDataId}")
    public void getAimTopogoramFile(@PathVariable Long ectDataId,
                                    @RequestParam String dataFormat,
                                    @RequestBody List<Pixel> pixels,
                                    HttpServletResponse response) throws IOException {

        Boolean separateInArray = dataFormat.equals("txt");
        Map<String, Object> result = getAimTopogram(ectDataId, separateInArray, pixels);

        convertToFileService.sendTopogram("topogramAim", ectDataId, dataFormat, result, response);
    }

    @GetMapping("/aim/graph/avg/{ectDataId}")
    public Map<String, Object> getAimAverage(@PathVariable Long ectDataId,
                                             @RequestParam(required = false) Boolean separateInArray) {
        Map<String, Object> result = new HashMap<>();
        if(separateInArray != null && separateInArray) {
            result = ectService.getAimAverageInArray(ectDataId);
        } else {
            List<ProcessedECTFrame> list = ectService.getAimAverage(ectDataId);
            result.put("content", list);
        }
        return result;
    }

    @GetMapping("/download/aim/graph/avg/{ectDataId}")
    public void getAimAverageFile(@PathVariable Long ectDataId,
                                  @RequestParam String dataFormat,
                                  HttpServletResponse response) throws IOException {

        Boolean separateInArray = dataFormat.equals("txt");
        Map<String, Object> result = getAimAverage(ectDataId, separateInArray);

        convertToFileService.sendGraph("averageAim", ectDataId, dataFormat, result, response);
    }

    @GetMapping("/anc/graph/avg/{ectDataId}")
    public Map<String, Object> getAncAverage(@PathVariable Long ectDataId,
                                             @RequestParam(required = false) Boolean separateInArray) {
        Map<String, Object> result = new HashMap<>();
        if(separateInArray != null && separateInArray) {
            result = ectService.getAncAverageInArray(ectDataId);
        } else {
            List<ProcessedECTFrame> list = ectService.getAncAverage(ectDataId);
            result.put("content", list);
        }
        return result;
    }

    @GetMapping("/download/anc/graph/avg/{ectDataId}")
    public void getAncAverageFile(@PathVariable Long ectDataId,
                                  @RequestParam String dataFormat,
                                  HttpServletResponse response) throws IOException {

        Boolean separateInArray = dataFormat.equals("txt");
        Map<String, Object> result = getAncAverage(ectDataId, separateInArray);

        convertToFileService.sendGraph("averageAnc", ectDataId, dataFormat, result, response);
    }

    @PostMapping("/anc/graph/{ectDataId}")
    public Map<String, Object> getAncGraph(@PathVariable Long ectDataId,
                                           @RequestParam(required = false) Boolean separateInArray,
                                           @RequestBody Pixel pixel) {
        Map<String, Object> result = new HashMap<>();
        if(separateInArray != null && separateInArray) {
            result = ectService.getAncGraphInArray(ectDataId, pixel);
        } else {
            List<ProcessedECTFrame> list = ectService.getAncGraph(ectDataId, pixel);
            result.put("content", list);
        }
        return result;
    }

    @PostMapping("/download/anc/graph/{ectDataId}")
    public void getAncGraphFile(@PathVariable Long ectDataId,
                                @RequestParam String dataFormat,
                                @RequestBody Pixel pixel,
                                HttpServletResponse response) throws IOException {

        Boolean separateInArray = dataFormat.equals("txt");
        Map<String, Object> result = getAncGraph(ectDataId, separateInArray, pixel);

        convertToFileService.sendGraph("graphAnc", ectDataId, dataFormat, result, response);
    }

}
