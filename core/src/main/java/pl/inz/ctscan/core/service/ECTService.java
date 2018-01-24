package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.db.DatabaseHelper;
import pl.inz.ctscan.core.utils.response.DbFormatConverter;
import pl.inz.ctscan.core.utils.response.ResultProducer;
import pl.inz.ctscan.db.ect.ECTDataRepository;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.db.file.FileDataRepository;
import pl.inz.ctscan.model.QueryOptions;
import pl.inz.ctscan.model.ect.*;
import pl.inz.ctscan.model.ect.utils.Pixel;
import pl.inz.ctscan.model.file.DataStatus;
import pl.inz.ctscan.model.file.FileData;
import pl.inz.ctscan.model.file.FileType;
import pl.inz.ctscan.model.response.PreparedPage;
import pl.inz.ctscan.model.response.ProcessedECTFrame;
import pl.inz.ctscan.model.response.TopogramECTValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ECTService {
    private final FrameRepository frameRepository;

    private final ECTDataRepository ectDataRepository;

    private final FileManager fileManager;

    private final DbFormatConverter dbFormatConverter;

    private final FileDataRepository fileDataRepository;

    @Autowired
    public ECTService(
            FileManager fileManager,
            FrameRepository frameRepository, ECTDataRepository ectDataRepository, DbFormatConverter dbFormatConverter, FileDataRepository fileDataRepository) {
        this.fileManager = fileManager;
        this.frameRepository = frameRepository;
        this.ectDataRepository = ectDataRepository;
        this.dbFormatConverter = dbFormatConverter;
        this.fileDataRepository = fileDataRepository;
    }

    public ECTData getECTData(Long ectDataId) {
        return ectDataRepository.findOne(ectDataId);
    }

    public Page<ECTData> getECTDataByUsername(String username, QueryOptions queryOptions) {
        PageRequest pageRequest = DatabaseHelper.preparePageRequest(queryOptions);

        return ectDataRepository.findByCreatedBy(username, pageRequest);
    }

    public List<ProcessedECTFrame> getAimGraph(Long ectDataId, Pixel pixel) {
        return prepareGraphValues(ectDataId, pixel);
    }

    private List<ProcessedECTFrame> prepareGraphValues(Long ectDataId, Pixel pixel) {
        ECTData ectData = getECTData(ectDataId);
        List<Frame> frames = getFrames(ectDataId);
         FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        List<ProcessedECTFrame> graphValues = frames.parallelStream()
                .map(f -> dbFormatConverter.processFrameToPreparedFrame(f, ectData, fileData.getFileType()))
                .map(pf -> new ProcessedECTFrame(pf.getMilliseconds(),
                        pf.getData().get(pixel.getRow()).get(pixel.getCol())))
                .collect(Collectors.toList());

        return graphValues;
    }

    public Map<String, Object> getAimGraphInArray(Long ectDataId, Pixel pixel) {
        return prepareGraphValuesInArray(ectDataId, pixel);
    }

    public Map<String,Object> getAncGraphInArray(Long ectDataId, Pixel pixel) {
        return prepareGraphValuesInArray(ectDataId, pixel);
    }

    private Map<String,Object> prepareGraphValuesInArray(Long ectDataId, Pixel pixel) {
        ECTData ectData = getECTData(ectDataId);
        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());
        List<Frame> frames = getFrames(ectDataId);

        List<Long> timeAxisX = new ArrayList<>();
        List<Float> valuesAxisY = new ArrayList<>();

        frames.stream()
                .map(f -> dbFormatConverter.processFrameToPreparedFrame(f, ectData, fileData.getFileType()))
                .forEach(pf -> {
                    timeAxisX.add(pf.getMilliseconds());
                    valuesAxisY.add(pf.getData().get(pixel.getRow()).get(pixel.getCol()));
                });

        return ResultProducer.prepareXY(timeAxisX, valuesAxisY);
    }

    public List<ProcessedECTFrame> getAimTopogram(Long ectDataId, List<Pixel> pixels) {
        ECTData ectData = getECTData(ectDataId);
        List<Frame> frames = getFrames(ectDataId);
        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        List<ProcessedECTFrame> topogramValues = frames.parallelStream()
                .map(f -> dbFormatConverter.processFrameToPreparedFrame(f, ectData, fileData.getFileType()))
                .map(pf -> {
                    List<TopogramECTValue> valuesPerTime = new ArrayList<>();
                    for(int i = 0; i < pixels.size(); i++ ) {
                        Pixel pixel = pixels.get(i);
                        Float value = pf.getData().get(pixel.getRow()).get(pixel.getCol());
                        valuesPerTime.add(new TopogramECTValue(value, pixel));
                    }
                    return new ProcessedECTFrame(pf.getMilliseconds(), valuesPerTime);
                })
                .collect(Collectors.toList());

        return topogramValues;
    }

    public Map<String,Object> getAimTopogramInArray(Long ectDataId, List<Pixel> pixels) {
        ECTData ectData = getECTData(ectDataId);
        List<Frame> frames = getFrames(ectDataId);
        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        List<Long> timeAxisX = new ArrayList<>();
        List<List<TopogramECTValue>> valuesAxisY = new ArrayList<>();

        frames.stream()
                .map(f -> dbFormatConverter.processFrameToPreparedFrame(f, ectData, fileData.getFileType()))
                .forEach(pf -> {
                    List<TopogramECTValue> valuesPerTime = new ArrayList<>();
                    for(int i = 0; i < pixels.size(); i++ ) {
                        Pixel pixel = pixels.get(i);
                        Float value = pf.getData().get(pixel.getRow()).get(pixel.getCol());
                        valuesPerTime.add(new TopogramECTValue(value, pixel));
                    }

                    timeAxisX.add(pf.getMilliseconds());
                    valuesAxisY.add(valuesPerTime);
                });

        return ResultProducer.prepareXY(timeAxisX, valuesAxisY);
    }

    public List<ProcessedECTFrame> getAimAverage(Long ectDataId) {
        return prepareAverage(ectDataId);
    }

    public Map<String, Object> getAimAverageInArray(Long ectDataId) {
        return prepareAverageInArray(ectDataId);
    }

    public Map<String, Object> getAncAverageInArray(Long ectDataId) {
        return prepareAverageInArray(ectDataId);
    }

    private Map<String,Object> prepareAverageInArray(Long ectDataId) {
        ECTData ectData = getECTData(ectDataId);
        List<Frame> frames = getFrames(ectDataId);
        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        List<Long> timeAxisX = new ArrayList<>();
        List<Float> valuesAxisY = new ArrayList<>();

        frames.stream()
                .map(f -> dbFormatConverter.processFrameToPreparedFrame(f, ectData, fileData.getFileType()))
                .forEach(pf -> {
                    timeAxisX.add(pf.getMilliseconds());
                    valuesAxisY.add(pf.getFrameAverage());
                });

        return ResultProducer.prepareXY(timeAxisX, valuesAxisY);
    }

    public List<ProcessedECTFrame> getAncAverage(Long ectDataId) {
        return prepareAverage(ectDataId);
    }

    private List<ProcessedECTFrame> prepareAverage(Long ectDataId) {
        ECTData ectData = getECTData(ectDataId);
        List<Frame> frames = getFrames(ectDataId);
        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        List<ProcessedECTFrame> averageValues = frames.parallelStream()
                .map(f -> dbFormatConverter.processFrameToPreparedFrame(f, ectData, fileData.getFileType()))
                .map(pf -> new ProcessedECTFrame(pf.getMilliseconds(), pf.getFrameAverage()))
                .collect(Collectors.toList());

        return averageValues;
    }

    public List<ProcessedECTFrame> getAncGraph(Long ectDataId, Pixel pixel) {
        return prepareGraphValues(ectDataId, pixel);
    }

    public ECTData addFramesFromFile(ECTData ectData) {
        changeEctDataStatus(ectData, DataStatus.PROCESSING);

        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        List<Frame> frames = fileManager.convertFileToFrames(ectData, fileData);

        frameRepository.save(frames);

        return changeEctDataStatus(ectData, DataStatus.FINISHED);
    }

    public List<Frame> getFrames(Long ectDataId) {
        return frameRepository.getFramesByEctDataId(ectDataId);
    }

    public Page<Frame> getFramesByPage(Long ectDataId, QueryOptions queryOptions) {
        PageRequest pageRequest = DatabaseHelper.preparePageRequest(queryOptions);

        return frameRepository.getFramesByEctDataId(ectDataId, pageRequest);
    }

    public ECTData changeEctDataStatus(Long ectDataId, DataStatus status) {
        ECTData ectData = getECTData(ectDataId);

        return changeEctDataStatus(ectData, status);
    }

    private ECTData changeEctDataStatus(ECTData ectData, DataStatus status) {
        ectData.setStatus(status);

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
        ectData.setFileDataId(fileData.getId());
        ectData.setStatus(DataStatus.TODO);

        return ectData;
    }

    public ECTData createECTData(FileData fileData, Long experimentId) {
        ECTData ectData = prepareECTData(fileData, experimentId);

        return ectDataRepository.save(ectData);
    }

    public PreparedPage<PreparedFrame> preparePageFromFrames(Page<Frame> frames, ECTData ectData) {
        PreparedPage<PreparedFrame> preparedPageFromFrames = createPreparedPage(frames);

        List<PreparedFrame> preparedFrames = preparedFramesFromFrames(frames.getContent(), ectData);

        preparedPageFromFrames.setContent(preparedFrames);

        return preparedPageFromFrames;
    }

    public List<PreparedFrame> preparedFramesFromFrames(List<Frame> frames, ECTData ectData) {
        FileData fileData = fileDataRepository.findOne(ectData.getFileDataId());

        return dbFormatConverter.getPreparedFrames(frames, ectData, fileData.getFileType());
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
