package pl.inz.ctscan.core.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageConverter {


    public void createImageByAWT(String path, String type) {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);

        Random rr = new Random();

        Color color = new Color(rr.nextInt(255), rr.nextInt(255), rr.nextInt(255));
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                image.setRGB(i, j, color.getRGB());
                color = new Color(rr.nextInt(255), rr.nextInt(255), rr.nextInt(255));
            }
        }

        try {
            ImageIO.write(image, type, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createImageByAWT(String path, String type, Color[][] colors) {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                image.setRGB(i, j, colors[i][j].getRGB());
            }
        }

        try {
            ImageIO.write(image, type, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
