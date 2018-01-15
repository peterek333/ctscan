package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.queue.QueueService;
import pl.inz.ctscan.db.ect.ECTDataRepository;
import pl.inz.ctscan.db.file.FileDataRepository;
import pl.inz.ctscan.model.QueryOptions;
import pl.inz.ctscan.model.ect.*;
import pl.inz.ctscan.model.ect.utils.Pixel;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;
import pl.inz.ctscan.model.response.ProcessedECTFrame;
import pl.inz.ctscan.model.response.PreparedPage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ect")
public class ECTController {

    private final ECTService ectService;

    private final QueueService queueService;

    @Autowired
    public ECTController(ECTService ectService, QueueService queueService) {
        this.ectService = ectService;
        this.queueService = queueService;
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
    public List<ProcessedECTFrame> getAimGraph(@PathVariable Long ectDataId,
                                               @RequestBody Pixel pixel) {
        return ectService.getAimGraph(ectDataId, pixel);
    }

    @PostMapping("/aim/topogram/{ectDataId}")
    public List<ProcessedECTFrame> getAimTopogram(@PathVariable Long ectDataId,
                                                  @RequestBody List<Pixel> pixels) {
        return ectService.getAimTopogram(ectDataId, pixels);
    }

    @PostMapping("/aim/graph/avg/{ectDataId}")
    public List<ProcessedECTFrame> getAimAverage(@PathVariable Long ectDataId) {
        return ectService.getAimAverage(ectDataId);
    }

    @PostMapping("/anc/graph/avg/{ectDataId}")
    public List<ProcessedECTFrame> getAncAverage(@PathVariable Long ectDataId) {
        return ectService.getAncAverage(ectDataId);
    }

    @PostMapping("/anc/graph/{ectDataId}")
    public List<ProcessedECTFrame> getAncGraph(@PathVariable Long ectDataId,
                                               @RequestBody Pixel pixel) {
        return ectService.getAncGraph(ectDataId, pixel);
    }

}
