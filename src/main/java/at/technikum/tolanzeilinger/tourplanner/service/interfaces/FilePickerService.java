package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.helpers.FileType;

import java.io.File;

public interface FilePickerService {
    File pickFile(FileType fileType);
}
