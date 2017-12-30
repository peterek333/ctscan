package pl.inz.ctscan.core.service.initalize;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.utils.FileConstants;
import pl.inz.ctscan.core.utils.FileManager;

import javax.annotation.PostConstruct;

@Service
public class InitializeService {

    @Value("${files.root.folder}")
    private String FILES_FOLDER;

    @PostConstruct
    public void init() {
        configureConstants();

        FileManager.createNecessaryDirectories();
    }

    private void configureConstants() {
        configureFileConstants();
    }

    private void configureFileConstants() {
        FileConstants temp = new FileConstants();

        temp.configureStaticFiles(FILES_FOLDER);
    }
}
