package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.ExperimentService;
import pl.inz.ctscan.model.experiment.Experiment;

import java.util.List;

@RestController
@RequestMapping("/experiment")
public class ExperimentController {

    @Autowired
    private ExperimentService experimentService;

    @PostMapping("/add")
    Experiment addExperiment(@RequestBody Experiment experiment) {
        return experimentService.addExperiment(experiment);
    }

    @GetMapping("/{experimentId}")
    Experiment getExperiment(@PathVariable("experimentId") Long id) {
        return experimentService.getExperiment(id);
    }

    @GetMapping
    List<Experiment> getListExperiment() {
        return experimentService.getListExperiment();
    }

    @PutMapping
    Experiment updateExperiment(@RequestBody Experiment experiment) {
        return experimentService.updateExperiment(experiment);
    }

    @DeleteMapping("/{experimentId}")
    Experiment deleteExperiment(@PathVariable("experimentId") Long id) {
        return experimentService.deleteExperiment(id);
    }

    @PostMapping("/list")
    List<Experiment> getListExperimentCreatedBy(@RequestBody String createdBy) {
        return experimentService.getListExperimentCreatedBy(createdBy);
    }
}
