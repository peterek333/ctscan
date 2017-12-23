package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.model.ect.ECTExperiment;
import pl.inz.ctscan.model.ect.Measurement;
import pl.inz.ctscan.model.x.XExperiment;

@RestController
@RequestMapping("/ect")
public class ECTController {

    @Autowired
    private ECTService ectService;

//    @GetMapping("/last")
//    Measurement getLastMeasurement() {
//        return ectService.getLastMeasurement();
//    }

    @PostMapping("/add")
    ECTExperiment addECTExperiment(@RequestBody ECTExperiment ectExperiment) {
        return ectService.addExperiment(ectExperiment);
    }

    @PostMapping("/addx")
    XExperiment addECTExperimentX(@RequestBody XExperiment ectExperiment) {
        return ectService.addExperiment(ectExperiment);
    }
}
