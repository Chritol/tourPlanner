package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

public interface PropertyLoaderService {
    String getProperty(String key);
    void setProperty(String key, String value);
}
