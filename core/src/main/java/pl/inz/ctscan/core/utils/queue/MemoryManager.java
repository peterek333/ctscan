package pl.inz.ctscan.core.utils.queue;

public class MemoryManager {

    public static final long MB_400_IN_BYTES = 400_000_000;//419_430_400; //in binary

    public static boolean freeMemoryLowerThan(long bytes) {
        return Runtime.getRuntime().freeMemory() < bytes;
    }
}
