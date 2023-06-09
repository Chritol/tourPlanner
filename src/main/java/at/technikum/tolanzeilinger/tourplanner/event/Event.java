package at.technikum.tolanzeilinger.tourplanner.event;

/**
 * Ok. Let me lay down some rules for naming Events:
 * Events thrown by Services will be named after the thing that happened.
 * Events thrown by the UI will be named after the thing that happened + _ACTION signifying a users intent to make something happen.
 *
 * Example:
 * TOUR_LOADED means that a service successfully fetched a tour.
 * NEW_LOG_ACTION means that a user wants to create a new log.
 */

public enum Event {
    // Prop Events
    PROPERTIES_LOADED,

    // Tour Planner Events
    TOUR_CREATED,
    TOUR_DELETED,
    TOUR_UPDATED,

    LOG_CREATED,
    LOG_DELETED,
    LOG_UPDATED,

    IMAGE_SAVED,



    // Bsp. Events
    CREATE_WORD,
    DELETE_WORD,


    NEW_TOUR_ACTION,
    REMOVE_TOUR_ACTION,
    OPEN_TOUR_ACTION,
    EDIT_TOUR_ACTION,
    EXIT_FORM_TOUR_ACTION,
    OPEN_MAP_ACTION,
    OPEN_MISC_ACTION,
    NEW_LOG_ACTION,
    EDIT_LOG_ACTION,
    REMOVE_LOG_ACTION,
    SELECT_NEW_ACTIVE_TOUR_ACTION,
    TOUR_CHANGED,
    ACTIVE_TOUR_CHANGED,
    OPEN_PICTURES_DIRECTORY_ACTION,
    OPEN_FILE_DIRECTORY_ACTION,
    OPEN_HELP_ACTION, PDF_CREATE_ACTION, COULD_NOT_CREATE_PDF, PDF_CREATED,
}
