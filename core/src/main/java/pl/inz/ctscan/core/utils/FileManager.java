package pl.inz.ctscan.core.utils;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.db.ect.TestFrameRowRepository;
import pl.inz.ctscan.model.ect.Frame;
import pl.inz.ctscan.model.ect.TestFrame;
import pl.inz.ctscan.model.ect.TestFrameRow;
import pl.inz.ctscan.model.file.ConverterMetadata;

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

    private void setNumberAndMilliseconds(TestFrame frame, String line) {
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

    public Map<String, Object> convertAimFileToFrames(String path) {

        long startTime = System.nanoTime();
        Path file = Paths.get(path);

        List<Frame> frames = new ArrayList<>();
        BigDecimal dataSum = new BigDecimal("0");
        BigDecimal rowSum;
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);

            Frame frame = null;
            StringJoiner csvValues = new StringJoiner(";");
            StringJoiner csvAverages = new StringJoiner(";");

            for (String line : (Iterable<String>) lines::iterator) {
                if (line.startsWith("## frame")) {
                    if (frame != null) {
                        frame.setData(csvValues.toString());
                        frame.setRowAverage(csvAverages.toString());
                        frames.add(frame);
                    }
                    frame = new Frame();

                    setNumberAndMilliseconds(frame, line);

                    csvValues = new StringJoiner(";");
                    csvAverages = new StringJoiner(";");
                } else if (line.length() > 0 && !line.startsWith("##")) {
                    //wszystkie biale znaki
                    String[] splitted = line.split("\\s+");
                    rowSum = new BigDecimal("0");

                    for (String data : splitted) {
                        float d = Float.parseFloat(data);
                        if (d > 0) {
                            csvValues.add("" + d);
                            rowSum = rowSum.add(new BigDecimal("" + d));
                        } else {
                            csvValues.add("" + 0);
                        }
                    }
                    rowSum = rowSum.divide(BigDecimal.valueOf(32));
                    csvAverages.add(rowSum.toString());
                    dataSum = dataSum.add(rowSum);
                }
            }
            if (frame != null) {
                frame.setData(csvValues.toString());
                frame.setRowAverage(csvAverages.toString());
                frames.add(frame);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        logger.info("Prepared measurement from file path: " + path);
        logger.info("Total elapsed time: " + elapsedTimeInMillis + " ms");

        int PRECISION = 2;
        String dataAverage = dataSum.divide(BigDecimal.valueOf(frames.size() * 32), PRECISION, RoundingMode.HALF_UP).toString();

        Map<String, Object> metaData = new HashMap<>();
        metaData.put(ConverterMetadata.FRAMES, frames);
        metaData.put(ConverterMetadata.DATA_AVERAGE, dataAverage);

        return metaData;
    }

    @Deprecated
    public List<TestFrame> convertAimFileToTestFrames(String path, TestFrameRowRepository testFrameRowRepository) {
        long startTime = System.nanoTime();
        Path file = Paths.get(path);

        List<TestFrame> frames = new ArrayList<>();
        List<TestFrameRow> frameRows = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);

            TestFrame frame = null;

            for (String line : (Iterable<String>) lines::iterator) {
                if (line.startsWith("## frame")) {
                    if (frame != null) {
                        frames.add(frame);
                    }
                    frame = new TestFrame();

                    setNumberAndMilliseconds(frame, line);

                    //frame.setRows(new ArrayList<>());
                } else if (line.length() > 0 && !line.startsWith("##")) {
                    //wszystkie biale znaki
                    String[] splitted = line.split("\\s+");

                    TestFrameRow row = new TestFrameRow();

                    for (String data : splitted) {
                        float d = Float.parseFloat(data);
                        if (d > 0) {
                            row.getValues().add(d);
                        } else {
                            row.getValues().add(0F);
                        }
                    }
                    //frame.getRows().add(row);
                    frameRows.add(row);
                }
            }
            if (frame != null) {
                frames.add(frame);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        logger.info("Prepared measurement from file path: " + path);
        logger.info("Total elapsed time: " + elapsedTimeInMillis + " ms");

        List<Float> f = frameRows.get(frameRows.size() - 1).getValues();
        List<Float> f1 = frameRows.get(frameRows.size() - 2).getValues();
        System.out.println("ost");

        testFrameRowRepository.save(frameRows);

        elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        logger.info("Total elapsed time2: " + elapsedTimeInMillis + " ms");

        return frames;
    }
}
