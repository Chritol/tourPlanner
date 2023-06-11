package at.technikum.tolanzeilinger.tourplanner.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameGenerator {
    public static String generateFileName(String rootPath, String fileName, FileType fileType, boolean unique) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        return rootPath +
                "/" +
                fileName +
                (unique ? "_" : "") +
                (unique ? currentTime : "") +
                fileType.getExtension();
    }

    public static String generateFileName(String rootPath, String fileName, FileType fileType) {
        return generateFileName(rootPath, fileName, fileType, true);
    }
}
