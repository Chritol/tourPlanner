package at.technikum.tolanzeilinger.tourplanner.helpers;

public enum FileType {
    PDF(".pdf"),
    EXCEL(".xlsx"),
    PNG(".png");
    private final String extension;
    FileType(String extension){
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
