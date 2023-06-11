package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

public interface FolderOpenerService {

    void openFileDirectory();
    void openPicturesDirectory();
    void openExcelDirectory();

    void createDirectoryIfNotExists(String directoryPath) ;
}
