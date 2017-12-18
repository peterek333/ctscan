package pl.inz.ctscan.core.utils.queue.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import pl.inz.ctscan.core.utils.queue.MemoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ProcessFileExecutor {

    private ExecutorService executor;

    private long executorNumber;

    private List<Runnable> tasks;

    private int MAX_THREADS;

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
            System.out.println("Dodaje task do listy");
            tasks.add(task);

            if(recreateThread == null) {
                System.out.println("Tworze recreateThread");

                recreateThread = new Thread(() -> {
                    while(!executor.isTerminated()) {
                        System.out.println("Czekam, az bedzie terminated");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Tworze nowy executor");
                    executor = createFixedThreadPool(MAX_THREADS);

                    executeTasksFromList();

                    recreateThread = null;
                });

                recreateThread.start();
            } else {
                System.out.println("Recreate zajete");
            }

        } else {
            System.out.println("Dodaje task");
            executor.execute(task);
        }

        System.out.println("Free " + Runtime.getRuntime().freeMemory());
        System.out.println("Max " + Runtime.getRuntime().maxMemory());
        System.out.println("Total " + Runtime.getRuntime().totalMemory());

        if(MemoryManager.freeMemoryLowerThan(MemoryManager.MB_400_IN_BYTES) && !executor.isShutdown()) {
            System.out.println("SHUTDOWN!!!");
            executor.shutdown();
        }
    }

    private void executeTasksFromList() {
        List<Runnable> tempTasks = new ArrayList<>();

        System.out.println("przed dodaniem " + tasks.size());

        for(Runnable task: tasks) {
            executor.execute(task);

            tempTasks.add(task);
        }

        tasks.removeAll(tempTasks);
        System.out.println("po usunieciu " + tasks.size());
    }
}
