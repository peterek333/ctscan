package pl.inz.ctscan.core.utils.queue;

import org.apache.log4j.Logger;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.db.ect.MeasurementRepository;
import pl.inz.ctscan.model.base.AbstractEntity;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.Measurement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProcessFileThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ProcessFileThread.class);

    private FileManager fileManager;

    private FrameRepository frameRepository;

    private MeasurementRepository measurementRepository;

    private String measurementId;

    private String path;

    public ProcessFileThread(FileManager fileManager, FrameRepository frameRepository,
                             MeasurementRepository measurementRepository, String measurementId, String path) {
        this.fileManager = fileManager;
        this.frameRepository = frameRepository;
        this.measurementRepository = measurementRepository;
        this.measurementId = measurementId;
        this.path = path;
    }

    @Override
    public void run() {
        logger.info("Watek o id: " + Thread.currentThread().getId() + " rozpoczal przetwarzanie pliku");
        Measurement measurement = measurementRepository.findOne(measurementId);
        if(measurement != null) {
            long startTime = System.nanoTime();
            List<Frame> frames = fileManager.processAimFrames(path);
            logger.info("Przetworzenie pliku: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));

            frames = frameRepository.save(frames);
            logger.info("Zapis " + frames.size() + " frames do bazy: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));

            measurement.setFramesId(frames.stream().map(AbstractEntity::getId).collect(Collectors.toList()));
            logger.info("Ustawienie framesId w measurement: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));

            measurementRepository.save(measurement);
            logger.info("Zapis measurement do bazy: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS));
        } else {
            logger.info("Brak obiektu measurement w bazie o id: " + measurementId);
        }
    }
}
