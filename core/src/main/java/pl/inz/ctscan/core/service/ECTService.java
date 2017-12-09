package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.db.MeasurementRepository;
import pl.inz.ctscan.model.ect.Measurement;

import java.util.List;

@Service
public class ECTService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public ECTService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Measurement getLastMeasurement() {
        return measurementRepository.findTopByOrderByCreatedDateDesc();
    }

}
