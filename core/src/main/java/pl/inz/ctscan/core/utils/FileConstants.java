package pl.inz.ctscan.core.utils;

public class FileConstants {
    public static String FILE_FOLDER = "";
    public static String FILE_AIM_PATH = FILE_FOLDER + "aim/";
    public static String FILE_ANC_PATH = FILE_FOLDER + "anc/";

    public static final String FILE_AIM_EXTENSION = ".aim";
    public static final String FILE_ANC_EXTENSION = ".anc";

    public static final int AIM_FRAME_SIZE_ROW = 32;
    public static final int AIM_FRAME_SIZE_COL = 32;

    public static final String CSV_SEPARATOR = ";";

    public void configureStaticFiles(String _file_folder) {
        FILE_FOLDER = _file_folder;
        FILE_AIM_PATH = FILE_FOLDER + "aim/";
        FILE_ANC_PATH = FILE_FOLDER + "anc/";
    }

}
