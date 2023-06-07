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
    IMAGE_SAVED, NEW_TOUR_ACTION, REMOVE_TOUR_ACTION, LOADED_TOUR_ACTION, EDIT_TOUR_ACTION, DELETE_WORD
}
