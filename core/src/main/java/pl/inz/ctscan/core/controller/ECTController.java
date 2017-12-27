package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.model.QueryOptions;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.PreparedFrame;
import pl.inz.ctscan.model.response.PreparedPage;

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
//    @GetMapping("/frames/{ectDataId}")
//    public List<Frame> getFrames(@PathVariable Long ectDataId) {
//        return ectService.getFrames(ectDataId);
//    }

    @PostMapping("/frames/{ectDataId}")
    public PreparedPage<PreparedFrame> getFramesByPage(@PathVariable Long ectDataId,
                                                       @RequestBody QueryOptions queryOptions) {
        Page<Frame> frames = ectService.getFramesByPage(ectDataId, queryOptions);

        return ectService.preparePageFromFrames(frames);
    }

}
