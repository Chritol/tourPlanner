package at.technikum.tolanzeilinger.tourplanner.event;

public enum Event {
    // Prop Events
    PROPERTIES_LOADED,

    // Tour Planner Events
    TOUR_LOADED,
    TOUR_CREATED,
    TOUR_DELETED,
    TOUR_UPDATED,

    LOG_CREATED,
    LOG_DELETED,
    LOG_UPDATED,



    // Bsp. Events
    CREATE_WORD,
    IMAGE_SAVED, DELETE_WORD
}
