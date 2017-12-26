package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.ExperimentService;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.experiment.Experiment;

@RestController
@RequestMapping("/ect")
public class ECTController {

    private final ECTService ectService;

    @Autowired
    public ECTController(ECTService ectService) {
        this.ectService = ectService;
    }

    @GetMapping("/data/{ectDataId}")
    public ECTData getECTData(@PathVariable("ectDataId") Long ectDataId) {
        return ectService.getECTData(ectDataId);
    }

}
