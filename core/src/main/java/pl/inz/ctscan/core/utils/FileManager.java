package pl.inz.ctscan.core.utils;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.db.ect.FrameRepository;
import pl.inz.ctscan.model.base.AbstractEntity;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.Measurement;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class);

    public Measurement readFileByJavaStream(String path, FrameRepository frameRepository) {

        long startTime = System.nanoTime();
        Path file = Paths.get(path);
        Measurement measurement = new Measurement();
        List<Frame> frames = new ArrayList<>();
        try
        {
            Stream<String> lines = Files.lines( file, StandardCharsets.UTF_8 );

            Frame frame = null;

            for( String line : (Iterable<String>) lines::iterator )
            {
                if(line.startsWith("## frame")) {
                    if(frame != null) {
                        //saveFrameAndAddToMeasurement(measurement, frame, frameRepository);
                        frames.add(frame);
                    }
                    frame = new Frame();

                    setNumberAndMilliseconds(frame, line);

                    frame.setData(new ArrayList<>());
                } else if(line.length() > 0 && !line.startsWith("##")) {
                    //wszystkie biale znaki
                    String[] splitted = line.split("\\s+");

                    frame.getData().add(new ArrayList<>());

                    for( String data: splitted) {
                        float d = Float.parseFloat(data);
                        if(d > 0) {
                            frame.getData().get(frame.getData().size() - 1).add(d);
                        } else {
                            frame.getData().get(frame.getData().size() - 1).add(0F);
                        }
                    }

                }
            }
            if(frame != null) {
                //saveFrameAndAddToMeasurement(measurement, frame, frameRepository);
                frames.add(frame);
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        frames = frameRepository.save(frames);

        //measurement.setFramesId(frames.stream().map(AbstractEntity::getId).collect(Collectors.toList()));

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        logger.info("Prepared measurement from file path: " + path);
        logger.info("Total elapsed time: " + elapsedTimeInMillis + " ms");

        return measurement;
    }

    public List<Frame> processAimFrames(String path) {
        logger.info("Start prepare frames from file path: " + path);
        long startTime = System.nanoTime();
        Path file = Paths.get(path);
        List<Frame> frames = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);

            Frame frame = null;

            for (String line : (Iterable<String>) lines::iterator) {
                if (line.startsWith("## frame")) {
                    if (frame != null) {
                        frames.add(frame);
                    }
                    frame = new Frame();

                    setNumberAndMilliseconds(frame, line);

                    frame.setData(new ArrayList<>());
                } else if (line.length() > 0 && !line.startsWith("##")) {
                    //wszystkie biale znaki
                    String[] splitted = line.split("\\s+");

                    frame.getData().add(new ArrayList<>());

                    for (String data : splitted) {
                        float d = Float.parseFloat(data);
                        if (d > 0) {
                            frame.getData().get(frame.getData().size() - 1).add(d);
                        } else {
                            frame.getData().get(frame.getData().size() - 1).add(0F);
                        }
                    }

                }
            }
            if (frame != null) {
                frames.add(frame);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //measurement.setFramesId(frames.stream().map(AbstractEntity::getId).collect(Collectors.toList()));

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        logger.info("Total elapsed time: " + elapsedTimeInMillis + " ms");

        return frames;
    }


    private void saveFrameAndAddToMeasurement(Measurement measurement, Frame frame, FrameRepository frameRepository) {
        frame = frameRepository.save(frame);

        measurement.getFramesId().add(frame.getId());
    }

    private void setNumberAndMilliseconds(Frame frame, String line) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        if(m.find()) {
            frame.setNumber(Long.parseLong(m.group(0)));
        }

        if(m.find()) {
            frame.setMilliseconds(Long.parseLong(m.group(0)));
        }
    }

    public void saveAimFile(MultipartFile file, String filePath) throws IOException {
        byte[] bytes = file.getBytes();

        Path path = Paths.get(filePath);

        Files.write(path, bytes);

        logger.info("File added. Path: " + filePath);
    }

    public String concatFilePath(String originalFilename, long suffix, String type) {
        String filename = originalFilename.substring(0, originalFilename.indexOf('.'));
        return FileConstants.FILE_AIM_PATH + filename + type + suffix;
    }

    public static String getFilenameFromFilePath(String filePath) {
        int OFFSET = 1;
        return filePath.substring(filePath.lastIndexOf('/') + OFFSET, filePath.length());
    }

    public static void createNecessaryDirectories() {
        String[] necessaryDirectories = {
                FileConstants.FILE_AIM_PATH
        };

        for(String directoryPath: necessaryDirectories) {
            File dir = new File(directoryPath);

            if(!dir.exists()) {
                boolean result = dir.mkdir();

                logger.info("Directory: " + directoryPath + " was created");
            } else {
                logger.info("Directory: " + directoryPath + " exists");
            }
        }
    }

    public static String getDirPathFromFilePath(String filePath) {
        int OFFSET = 1;
        return filePath.substring(0, filePath.lastIndexOf('/') + OFFSET);
    }
}
