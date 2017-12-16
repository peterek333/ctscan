package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.inz.ctscan.core.utils.FileManager;

import java.io.IOException;

@Service
public class UploadService {



    public void uploadFile(MultipartFile file) throws IOException {
        long suffix = 1L;
        FileManager.saveAimFile(file, suffix);
    }
}
