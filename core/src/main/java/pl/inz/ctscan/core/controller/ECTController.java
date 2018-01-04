package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.db.ect.ECTDataRepository;
import pl.inz.ctscan.db.file.FileDataRepository;
import pl.inz.ctscan.model.QueryOptions;
import pl.inz.ctscan.model.ect.*;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FILEData;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;
import pl.inz.ctscan.model.response.PreparedPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/ect")
public class ECTController {

    private final ECTService ectService;

    @Autowired
    public ECTController(ECTService ectService) {
        this.ectService = ectService;
    }

    @GetMapping("/data/{ectDataId}")
    public ECTData getECTData(@PathVariable Long ectDataId) {
        return ectService.getECTData(ectDataId);
    }

    //Endpoint jest pamięciożerny ze względu na dużą ilość danych
    @GetMapping("/frames/{ectDataId}")
    public List<Frame> getFrames(@PathVariable Long ectDataId) {
        System.out.println("t");
        long startTime = System.nanoTime();
        List<Frame> frames = ectService.getFrames(ectDataId);

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        System.out.println("t: " + elapsedTimeInMillis);

        return frames;
    }

    @PostMapping("/frames/{ectDataId}")
    public PreparedPage<PreparedFrame> getFramesByPage(@PathVariable Long ectDataId,
                                                       @RequestBody QueryOptions queryOptions) {
        Page<Frame> frames = ectService.getFramesByPage(ectDataId, queryOptions);

        return ectService.preparePageFromFrames(frames);
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

    @GetMapping("/tt2")
    public void tt2() {
        ECTDataAIM ectDataANC = (ECTDataAIM) ectDataRepository.findByFileData_FileType(FileType.AIM);

        FileData fileData = fileDataRepository.findByEctData_ExperimentId(1L);

        System.out.println("f");

    }

}
