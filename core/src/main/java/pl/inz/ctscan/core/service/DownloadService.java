package pl.inz.ctscan.core.service;

import org.springframework.stereotype.Service;
import pl.inz.ctscan.model.file.FileData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.google.common.io.ByteStreams.toByteArray;

@Service
public class DownloadService {

    public byte[] getFileBytes(FileData fileData) throws Exception {
         String fileNameInSystem = fileData.getFullPath();
         File file = new File(fileNameInSystem);

         if(file.exists()) {
             InputStream inputStream = new FileInputStream(file);

             return toByteArray(inputStream);
         } else {
             throw new Exception("File doesn't exists");
         }
    }
}
