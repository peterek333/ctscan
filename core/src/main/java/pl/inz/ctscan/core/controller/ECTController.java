package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
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

    @Autowired
    public ECTController(ECTService ectService) {
        this.ectService = ectService;
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

    @Autowired
    ECTDataRepository ectDataRepository;

    @Autowired
    FileDataRepository fileDataRepository;

    @GetMapping("/tt")
    public void tt() {
        ECTDataANC ectDataANC = new ECTDataANC();
        ectDataANC.setElectrodes(8);
        ectDataANC.setExperimentId(1L);
        ectDataANC.setStatus(DataStatus.TODO);

        FileData fileData = new FileData();
        fileData.setFullPath("blabla");
        fileData.setFileType(FileType.ANC);

        fileData.setEctData(ectDataANC);

        ectDataANC.setFileData(fileData);

        ectDataRepository.save(ectDataANC);
        fileDataRepository.save(fileData);

        ECTDataAIM ectDataANC2 = new ECTDataAIM();
        ectDataANC2.setElectrodes(12);
        ectDataANC2.setExperimentId(2L);
        ectDataANC2.setPixels(812);
        ectDataANC2.setStatus(DataStatus.PROCESSING);

        FileData fileData2 = new FileData();
        fileData2.setFullPath("blabla2");
        fileData2.setFileType(FileType.AIM);

        fileData2.setEctData(ectDataANC2);

        ectDataANC2.setFileData(fileData2);

        ectDataRepository.save(ectDataANC2);
        fileDataRepository.save(fileData2);

        System.out.println("zrobione");

    }
    @GetMapping("/tt3")
    public void tt3() {

        String s = "## Electrodes = 8, Measurements = 28";
        String s2 = "## Data for Plane 1";

        System.out.println("i: " + s.indexOf('='));
        System.out.println("i2: " + s.substring(s.indexOf('='), s.length()));
        String str = s.substring(s.indexOf('='), s.indexOf(','));
        str = str.replaceAll("\\D+","");
        System.out.println("i2: " + Integer.parseInt(str));
        System.out.println("mam: " + s2.indexOf("Plane"));

        int startIndex = s.indexOf('=');
        int endIndex = s.indexOf(',');
        String electrodes = s.substring(startIndex, endIndex);
        startIndex = s.indexOf('=', endIndex);
        endIndex = s.indexOf(',', startIndex);
        endIndex = endIndex == -1 ? s.length() : endIndex;
        String meas = s.substring(startIndex, endIndex);

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        if (m.find()) {
            System.out.println(Integer.parseInt(m.group(0)));
        }

        if (m.find()) {
            System.out.println(Integer.parseInt(m.group(0)));
        }

        System.out.println("mam: " + s2.indexOf("Plane"));
    }

    @GetMapping("/tt2")
    public void tt2() {
        ECTDataAIM ectDataANC = (ECTDataAIM) ectDataRepository.findByFileData_FileType(FileType.AIM);

        FileData fileData = fileDataRepository.findByEctData_ExperimentId(1L);

        System.out.println("f");

    }

}
