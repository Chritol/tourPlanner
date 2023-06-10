package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.helpers.FileType;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.FilePickerService;
import javafx.stage.FileChooser;

import java.io.File;

public class FilePickerServiceImpl implements FilePickerService {

    @Override
    public File pickFile(FileType fileType) {
        FileChooser fileChooser = new FileChooser();

        if(fileType != null)
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("(*" + fileType.getExtension() + ")", "*" + fileType.getExtension()));

        return fileChooser.showOpenDialog(null);
    }
}
