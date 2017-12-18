package pl.inz.ctscan.core.utils.queue;

public class MemoryManager {

    public static final long MB_400_IN_BYTES = 419_430_400;

    public static boolean freeMemoryLowerThan(long bytes) {
        return Runtime.getRuntime().freeMemory() < bytes;
    }
}
