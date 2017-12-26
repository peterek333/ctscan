package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.db.ect.ECTDataRepository;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.db.ect.TestFrameRepository;
import pl.inz.ctscan.db.ect.TestFrameRowRepository;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.TestFrame;
import pl.inz.ctscan.model.file.ConverterMetadata;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FileData;

import java.util.List;
import java.util.Map;

@Service
public class ECTService {
    private final FrameRepository frameRepository;

    private final TestFrameRepository testFrameRepository;

    private final ECTDataRepository ectDataRepository;

    final
    FileManager fileManager;

    @Autowired
    public ECTService(
            FileManager fileManager,
            FrameRepository frameRepository, TestFrameRepository testFrameRepository, ECTDataRepository ectDataRepository) {
        this.fileManager = fileManager;
        this.frameRepository = frameRepository;
        this.testFrameRepository = testFrameRepository;
        this.ectDataRepository = ectDataRepository;
    }

    public ECTData getECTData(Long ectDataId) {
        return ectDataRepository.findOne(ectDataId);
    }

    public ECTData addFramesFromFile(String path, ECTData ectData) {
        changeEctDataStatus(ectData, DataStatus.PROCESSING);

        Map<String, Object> data = fileManager.convertAimFileToFrames(path);

        List<Frame> frames = (List<Frame>) data.get(ConverterMetadata.FRAMES);
        frames.parallelStream().forEach(f -> f.setEctDataId(ectData.getId()));
        frameRepository.save(frames);

        String avg = (String) data.get(ConverterMetadata.DATA_AVERAGE);
        ectData.setDataAverage(avg);
        ectData.setStatus(DataStatus.FINISHED);

        return ectDataRepository.save(ectData);
    }

    private void changeEctDataStatus(ECTData ectData, DataStatus processing) {
        ectData.setStatus(processing);

        ectDataRepository.save(ectData);
    }

    private ECTData prepareECTData(Long fileDataId, Long experimentId) {
        return ECTData.builder()
                .fileDataId(fileDataId)
                .experimentId(experimentId)
                .status(DataStatus.TODO)
                .build();
    }

    @Autowired
    private TestFrameRowRepository testFrameRowRepository;

    public void addTestFramesFromFile(FileData fileData) {
        List<TestFrame> frames = fileManager.convertAimFileToTestFrames(fileData.getFullPath(), testFrameRowRepository);

        testFrameRepository.save(frames);
        System.out.println("d");
    }

    public ECTData createECTData(FileData fileData, Long experimentId) {
        ECTData ectData = prepareECTData(fileData.getId(), experimentId);

        return ectDataRepository.save(ectData);
    }
}
