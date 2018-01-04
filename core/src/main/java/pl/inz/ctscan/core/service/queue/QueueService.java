package pl.inz.ctscan.core.service.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.utils.queue.ProcessFileExecutor;
import pl.inz.ctscan.core.utils.queue.ProcessFileThread;
import pl.inz.ctscan.model.ect.ECTData;

@Service
public class QueueService {

    private final ProcessFileExecutor processFileExecutor;

    private final ECTService ectService;

    @Autowired
    public QueueService(ProcessFileExecutor processFileExecutor, ECTService ectService) {
        this.processFileExecutor = processFileExecutor;
        this.ectService = ectService;
    }

    public void processFrames(Long ectDataId) throws InterruptedException {
        ECTData ectData = ectService.getECTData(ectDataId);

        ProcessFileThread processFileThread = new ProcessFileThread(ectService, ectData);

        processFileExecutor.execute(processFileThread);
    }

}
