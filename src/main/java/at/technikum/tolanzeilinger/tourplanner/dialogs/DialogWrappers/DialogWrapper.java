package at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers;

public interface DialogWrapper {
    void setTitle(String title);

    void setMessage(String message);

    void show();

    void setOptions(Object options);
}
