package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.db.ect.TestFrameRepository;
import pl.inz.ctscan.db.ect.TestFrameRowRepository;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.TestFrame;
import pl.inz.ctscan.model.file.FileData;

import java.util.List;

@Service
public class ECTService {
//
//    private final MeasurementRepository measurementRepository;
//
    private final FrameRepository frameRepository;

    private final TestFrameRepository testFrameRepository;

    final
    FileManager fileManager;

    @Autowired
    public ECTService(
            FileManager fileManager,
            FrameRepository frameRepository, TestFrameRepository testFrameRepository) {
        this.fileManager = fileManager;
        this.frameRepository = frameRepository;
        this.testFrameRepository = testFrameRepository;
    }

/*
    public boolean experimentExist(Long experimentId) {
        ECTExperiment ectExperiment = experimentRepository.findOne(experimentId);

        return ectExperiment != null;
    }
*/
    public void addFramesFromFile(FileData fileData) {
        List<Frame> frames = fileManager.convertAimFileToFrames(fileData.getFullPath());

        frameRepository.save(frames);
        System.out.println("d");
    }

    @Autowired
    private TestFrameRowRepository testFrameRowRepository;

    public void addTestFramesFromFile(FileData fileData) {
        List<TestFrame> frames = fileManager.convertAimFileToTestFrames(fileData.getFullPath(), testFrameRowRepository);

        testFrameRepository.save(frames);
        System.out.println("d");
    }

}
