package pl.inz.ctscan.core.service.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.core.utils.queue.ProcessFileExecutor;
import pl.inz.ctscan.core.utils.queue.ProcessFileThread;
import pl.inz.ctscan.model.ect.ECTData;
import pl.inz.ctscan.model.file.DataStatus;

@Service
public class QueueService {

    private final ProcessFileExecutor processFileExecutor;

    private final ECTService ectService;

    @Autowired
    public QueueService(ProcessFileExecutor processFileExecutor, ECTService ectService) {
        this.processFileExecutor = processFileExecutor;
        this.ectService = ectService;
    }

    public boolean processFrames(Long ectDataId) throws Exception {
        ECTData ectData = ectService.getECTData(ectDataId);

        if(ectData.getStatus() == DataStatus.TODO) {
            ProcessFileThread processFileThread = new ProcessFileThread(ectService, ectDataId);

            ectService.changeEctDataStatus(ectDataId, DataStatus.IN_QUEUE);

            processFileExecutor.execute(processFileThread);

            return true;
        } else {
            throw new Exception("Can't process file. File is in status: " + ectData.getStatus());
        }
    }

}
