package pl.inz.ctscan.core.service.queue;

//@Service
public class QueueService {
/*
    private final ProcessFileExecutor processFileExecutor;

    private final FileManager fileManager;

    private final FrameRepository frameRepository;

    private final MeasurementRepository measurementRepository;

    private BlockingQueue<ProcessFileThread> processFileThreadsQueue;

    @Autowired
    public QueueService(ProcessFileExecutor processFileExecutor, MeasurementRepository measurementRepository, FileManager fileManager, FrameRepository frameRepository) {
        this.processFileExecutor = processFileExecutor;
        this.measurementRepository = measurementRepository;
        this.fileManager = fileManager;
        this.frameRepository = frameRepository;

        int MAX_THREADS = 2;
        processFileThreadsQueue = new ArrayBlockingQueue<>(MAX_THREADS);
    }

    public void processAimFrames(Long measurementId, String path) throws InterruptedException {
        ProcessFileThread processFileThread =
                new ProcessFileThread(fileManager, frameRepository, measurementRepository, measurementId, path);

        processFileExecutor.execute(processFileThread);
    }

    public void processAimFramesByConsumer(String measurementId, String path) {

    }
*/
}
