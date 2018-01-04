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
import pl.inz.ctscan.model.ect.*;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;
import pl.inz.ctscan.model.response.PreparedPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    class Pair {
        public Integer row;
        public Integer col;

        public Pair(Integer row, Integer col) {
            this.row = row;
            this.col = col;
        }
    }

    class FloatIndex {
        public Float f;
        public Long id;

        public FloatIndex(Float f, Long id) {
            this.f = f;
            this.id = id;
        }
    }

    public ECTData addFramesFromFile(ECTData ectData) {
        changeEctDataStatus(ectData, DataStatus.PROCESSING);

        List<Frame> frames = fileManager.convertFileToFrames(ectData);

        long startTime = System.nanoTime();
        frameRepository.save(frames);
        System.out.println("time1s: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));
        frames = frameRepository.getFramesByEctDataId(ectData.getId());
        System.out.println("time2get: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));

        List<Float> pframes = frames.parallelStream()
                .map(dbFormatConverter::processFrameToPreparedFrame)
                .map(pf -> pf.getData().get(3).get(2))
                .collect(Collectors.toList());
        System.out.println("time3: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));

        List<Pair> pair = new ArrayList<>();
        pair.add(new Pair(3, 2));
        pair.add(new Pair(4, 1));
        pair.add(new Pair(4, 2));
        pair.add(new Pair(4, 3));
        pair.add(new Pair(5, 2));


        List<List<FloatIndex>> fl = frames.parallelStream()
                .map(dbFormatConverter::processFrameToPreparedFrame)
                .map(pf -> {
                    List<FloatIndex> l = new ArrayList<>();
                    for(int i = 0; i < pair.size(); i++ ) {
                        l.add(new FloatIndex(pf.getData().get(pair.get(i).row).get(pair.get(i).col), pf.getId()));
                    }
                    return l;
                })
                .collect(Collectors.toList());
        System.out.println("time4: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));

        Long old = fl.get(0).get(0).id;
        for(int i = 1; i < fl.size(); i++) {
            if(old != (fl.get(i).get(0).id - 1)) {
                System.out.println("nie rowne! " + old + " / " + fl.get(i).get(0).id);
            }
            old++;
        }
        System.out.println("time5: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));
        System.out.println("f");

        return changeEctDataStatus(ectData, DataStatus.FINISHED);
    }

    public List<Frame> getFrames(Long ectDataId) {
        return frameRepository.getFramesByEctDataId(ectDataId);
    }

    public Page<Frame> getFramesByPage(Long ectDataId, QueryOptions queryOptions) {
        PageRequest pageRequest = DatabaseHelper.preparePageRequest(queryOptions);

        return frameRepository.getFramesByEctDataId(ectDataId, pageRequest);
    }

    private ECTData changeEctDataStatus(ECTData ectData, DataStatus processing) {
        ectData.setStatus(processing);

        return ectDataRepository.save(ectData);
    }

    private ECTData prepareECTData(FileData fileData, Long experimentId) {
        ECTData ectData;

        if(fileData.getFileType() == FileType.AIM) {
            ectData = new ECTDataAIM();
        } else {
            ectData = new ECTDataANC();
        }

        ectData.setExperimentId(experimentId);
        ectData.setFileData(fileData);
        ectData.setStatus(DataStatus.TODO);

        return ectData;
    }

    @Autowired
    private TestFrameRowRepository testFrameRowRepository;

    public void addTestFramesFromFile(FileData fileData) {
        List<TestFrame> frames = fileManager.convertAimFileToTestFrames(fileData.getFullPath(), testFrameRowRepository);

        testFrameRepository.save(frames);
        System.out.println("d");
    }

    public ECTData createECTData(FileData fileData, Long experimentId) {
        ECTData ectData = prepareECTData(fileData, experimentId);

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
