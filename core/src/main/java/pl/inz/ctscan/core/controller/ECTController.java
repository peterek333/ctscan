package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.model.ect.Measurement;

@RestController
@RequestMapping("/ect")
public class ECTController {

    @Autowired
    private ECTService ectService;

    @GetMapping("/last")
    Measurement getLastMeasurement() {
        return ectService.getLastMeasurement();
    }

}
