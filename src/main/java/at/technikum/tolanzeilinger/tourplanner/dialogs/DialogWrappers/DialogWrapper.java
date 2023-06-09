package at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers;

public interface DialogWrapper<R> {
    void setTitle(String title);

    void setMessage(String message);

    void show();

    R showAndReturn();

    void setOptions(R options);
}
