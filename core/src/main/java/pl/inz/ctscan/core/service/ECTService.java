package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.db.DatabaseHelper;
import pl.inz.ctscan.core.utils.response.DbFormatConverter;
import pl.inz.ctscan.db.ect.ECTDataRepository;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.db.ect.TestFrameRepository;
import pl.inz.ctscan.db.ect.TestFrameRowRepository;
import pl.inz.ctscan.model.QueryOptions;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.PreparedFrame;
import pl.inz.ctscan.model.ect.TestFrame;
import pl.inz.ctscan.model.file.ConverterMetadata;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.response.PreparedPage;

import java.util.List;
import java.util.Map;

@Service
public class ECTService {
    private final FrameRepository frameRepository;

    private final TestFrameRepository testFrameRepository;

    private final ECTDataRepository ectDataRepository;

    private final FileManager fileManager;

    private final DbFormatConverter dbFormatConverter;

    @Autowired
    public ECTService(
            FileManager fileManager,
            FrameRepository frameRepository, TestFrameRepository testFrameRepository, ECTDataRepository ectDataRepository, DbFormatConverter dbFormatConverter) {
        this.fileManager = fileManager;
        this.frameRepository = frameRepository;
        this.testFrameRepository = testFrameRepository;
        this.ectDataRepository = ectDataRepository;
        this.dbFormatConverter = dbFormatConverter;
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

    public List<Frame> getFrames(Long ectDataId) {
        return frameRepository.getFramesByEctDataId(ectDataId);
    }

    public Page<Frame> getFramesByPage(Long ectDataId, QueryOptions queryOptions) {
        PageRequest pageRequest = DatabaseHelper.preparePageRequest(queryOptions);

        return frameRepository.getFramesByEctDataId(ectDataId, pageRequest);
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

    public PreparedPage<PreparedFrame> preparePageFromFrames(Page<Frame> frames) {
        PreparedPage<PreparedFrame> preparedPageFromFrames = createPreparedPage(frames);

        List<PreparedFrame> preparedFrames = dbFormatConverter.getPreparedFrames(frames.getContent());

        preparedPageFromFrames.setContent(preparedFrames);

        return preparedPageFromFrames;
    }

    private PreparedPage<PreparedFrame> createPreparedPage(Page<Frame> frames) {
        return PreparedPage.<PreparedFrame>builder()
                .first(frames.isFirst())
                .last(frames.isLast())
                .number(frames.getNumber())
                .numberOfElements(frames.getNumberOfElements())
                .size(frames.getSize())
                .sort(frames.getSort())
                .totalElements(frames.getTotalElements())
                .totalPages(frames.getTotalPages())
                .build();
    }
}
