package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.ImageConverter;
import pl.inz.ctscan.db.ect.MeasurementRepository;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Random;

@Service
public class FileService {

    final FileManager fileManager;

    final MeasurementRepository measurementRepository;

    final ImageConverter imageConverter;

    @Autowired
    public FileService(FileManager fileManager, MeasurementRepository measurementRepository, ImageConverter imageConverter) {
        this.fileManager = fileManager;
        this.measurementRepository = measurementRepository;
        this.imageConverter = imageConverter;
    }


    @PostConstruct
    public void init() {
        //Measurement m = fileManager.readFileByJavaStream("/home/neo/_argo/tomografia/test/test_3.aim");

        //measurementRepository.save(m);

        //testImages();
    }

    private void testImages() {
        String type = "jpg";
        String path = "/home/neo/_argo/tomografia/test/test_image" + "." + type;

        Random rr = new Random();

        Color[][] colors = new Color[32][32];
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                colors[i][j] = new Color(rr.nextInt(255), rr.nextInt(255), rr.nextInt(255));
            }
        }

        imageConverter.createImageByAWT("/home/neo/_argo/tomografia/test/test_image2.jpg", "jpg", colors);

        imageConverter.createImageByAWT("/home/neo/_argo/tomografia/test/test_image2.png", "png", colors);

    }
}
