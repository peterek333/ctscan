package pl.inz.ctscan.core.utils.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ProcessFileExecutor {

    private ExecutorService executor;
    private long executorNumber;
    private int MAX_THREADS;

    private List<Runnable> tasks;
    private Thread recreateThread;

    public ProcessFileExecutor(int MAX_THREADS) {
        executorNumber = 0;

        executor = createFixedThreadPool(MAX_THREADS);

        tasks = new ArrayList<>();

        this.MAX_THREADS = MAX_THREADS;
    }

    private ExecutorService createFixedThreadPool(int MAX_THREADS) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("ProcessFileExecutor-" + executorNumber++ + "-%d")
                .setDaemon(true)
                .build();

        return Executors.newFixedThreadPool(MAX_THREADS, threadFactory);
    }

    public void execute(Runnable task) {
        if(executor.isShutdown()) {
            tasks.add(task);

            if(recreateThread == null) {
                recreateThread = new Thread(() -> {
                    while(!executor.isTerminated()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    executor = createFixedThreadPool(MAX_THREADS);

                    executeTasksFromList();

                    recreateThread = null;
                });

                recreateThread.start();
            }
        } else {
            executor.execute(task);
        }

        if(MemoryManager.freeMemoryLowerThan(MemoryManager.MB_400_IN_BYTES) && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    private void executeTasksFromList() {
        List<Runnable> tempTasks = new ArrayList<>();

        for(Runnable task: tasks) {
            executor.execute(task);

            tempTasks.add(task);
        }

        tasks.removeAll(tempTasks);
    }
}
