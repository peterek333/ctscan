package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.service.ExperimentService;
import pl.inz.ctscan.model.experiment.Experiment;

@RestController
@RequestMapping("/ect")
public class ECTController {

    @Autowired
    private ECTService ectService;
    

//    @GetMapping("/last")
//    Measurement getLastMeasurement() {
//        return ectService.getLastMeasurement();
//    }

}
