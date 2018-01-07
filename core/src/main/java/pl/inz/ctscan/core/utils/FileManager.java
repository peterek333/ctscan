package pl.inz.ctscan.core.utils;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.db.ect.TestFrameRowRepository;
import pl.inz.ctscan.model.ect.*;
import pl.inz.ctscan.model.file.FileType;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class);

    private void setNumberAndMilliseconds(Frame frame, String line) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        if (m.find()) {
            frame.setNumber(Long.parseLong(m.group(0)));
        }

        if (m.find()) {
            frame.setMilliseconds(Long.parseLong(m.group(0)));
        }
    }

    public void saveAimFile(MultipartFile file, String filePath) throws IOException {
        byte[] bytes = file.getBytes();

        Path path = Paths.get(filePath);

        Files.write(path, bytes);

        logger.info("File added. Path: " + filePath);
    }

    public String concatFilePath(String originalFilename, String type) {
        String filename = originalFilename.substring(0, originalFilename.indexOf('.'));
        return FileConstants.FILE_AIM_PATH + filename + type;
    }

    public String concatFilePath(String originalFilename, long suffix, String type) {
        String filename = originalFilename.substring(0, originalFilename.indexOf('.'));
        return FileConstants.FILE_AIM_PATH + filename + type + suffix;
    }

    public static void createNecessaryDirectories() {
        String[] necessaryDirectories = {
                FileConstants.FILE_FOLDER,
                FileConstants.FILE_AIM_PATH
        };

        for (String directoryPath : necessaryDirectories) {
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                boolean result = dir.mkdir();

                logger.info("Directory: " + directoryPath + " was created");
            } else {
                logger.info("Directory: " + directoryPath + " exists");
            }
        }
    }

    public static String getFilenameFromFilePath(String filePath) {
        int OFFSET = 1;
        return filePath.substring(filePath.lastIndexOf('/') + OFFSET, filePath.length());
    }

    public static String getDirPathFromFilePath(String filePath) {
        int OFFSET = 1;
        return filePath.substring(0, filePath.lastIndexOf('/') + OFFSET);
    }

    public List<Frame> convertFileToFrames(ECTData ectData) {
        long startTime = System.nanoTime();

        FileType fileType = ectData.getFileData().getFileType();
        String path = ectData.getFileData().getFullPath();
        Path file = Paths.get(path);

        List<Frame> frames = new ArrayList<>();
        BigDecimal frameSum = null;
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);

            Frame frame = null;
            StringJoiner csvValues = new StringJoiner(";");

            for (String line : (Iterable<String>) lines::iterator) {
                if (line.startsWith("## frame")) {
                    if (frame != null) {
                        addFrame(frames, frame, ectData, csvValues, frameSum);
                    }
                    frame = new Frame();
                    frameSum = new BigDecimal("0");

                    setNumberAndMilliseconds(frame, line);

                    csvValues = new StringJoiner(";");
                } else if (line.length() > 0 && !line.startsWith("##")) {
                    //wszystkie biale znaki
                    String[] splitted = line.split("\\s+");

                    for (String data : splitted) {
                        float d = Float.parseFloat(data);
                        if (d > 0) {
                            csvValues.add("" + d);
                            frameSum = frameSum.add(new BigDecimal("" + d));
                        } else {
                            csvValues.add("" + 0);
                        }
                    }
                } else if (line.startsWith("## Electrodes")) {
                    getMetadata(line, fileType, ectData);
                } else if (line.startsWith("## Data")) {
                    getPlaneNumber(line, ectData);
                }
            }
            if (frame != null) {
                addFrame(frames, frame, ectData, csvValues, frameSum);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        logger.info("Prepared measurement from file path: " + path);
        logger.info("Total elapsed time: " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS) + " ms");

        return frames;
    }

    private void addFrame(List<Frame> frames, Frame frame, ECTData ectData, StringJoiner csvValues, BigDecimal frameSum) {
        frame.setEctDataId(ectData.getId());
        frame.setData(csvValues.toString());

        Float average;
        if(ectData.getFileData().getFileType() == FileType.AIM) {
            Integer pixels = ((ECTDataAIM) ectData).getPixels();
            average = divideFrameSumBy(frameSum, pixels);
        } else {
            average = divideFrameSumBy(frameSum, ectData.getMeasurements());
        }
        frame.setFrameAverage(average);
        frames.add(frame);
    }

    private Float divideFrameSumBy(BigDecimal frameSum, Integer divider) {
        int PRECISION = 4;
        BigDecimal average = frameSum.divide(BigDecimal.valueOf(divider), PRECISION, RoundingMode.HALF_UP);

        return average.floatValue();
    }

    private void getPlaneNumber(String line, ECTData ectData) {
        String plane = line.replaceAll("\\D+","");

        ectData.setPlane(Integer.parseInt(plane));
    }

    private void getMetadata(String line, FileType fileType, ECTData ectData) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        if (m.find()) {
            ectData.setElectrodes(Integer.parseInt(m.group(0)));
        }

        if (m.find()) {
            ectData.setMeasurements(Integer.parseInt(m.group(0)));
        }

        if(fileType == FileType.AIM && m.find()) {
            ((ECTDataAIM) ectData).setPixels(Integer.parseInt(m.group(0)));
        }
    }
}
