package pl.inz.ctscan.core.utils.queue;

import org.apache.log4j.Logger;
import pl.inz.ctscan.core.service.ECTService;
import pl.inz.ctscan.model.ect.ECTData;

public class ProcessFileThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ProcessFileThread.class);

    private ECTService ectService;

    private Long ectDataId;

    public ProcessFileThread(ECTService ectService, Long ectDataId) {
        this.ectService = ectService;
        this.ectDataId = ectDataId;
    }

    @Override
    public void run() {
        logger.info("Watek o id: " + Thread.currentThread().getId() + " rozpoczal przetwarzanie pliku");

        if(ectDataId != null) {
            ECTData ectData = ectService.getECTData(ectDataId);

            ectService.addFramesFromFile(ectData);
            logger.info("Zakończone przetwarzania w watku: " + Thread.currentThread().getId());
        }
    }
}
