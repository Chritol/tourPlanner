package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Log4jLogger;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.view.MainController;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;

public class ViewFactory {
    private static ViewFactory instance;

    private final EventAggregator eventAggregator;


    // Repositories
    private final WordRepository wordRepository;

    // View Models
    private final MainViewModel mainViewModel;

    // Logger
    private final Logger logger;

    private ViewFactory() {

        this.logger = new Log4jLogger();
        this.eventAggregator = new EventAggregator();

        // initialize Repositories
        this.wordRepository = new WordRepository(eventAggregator);

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(eventAggregator, wordRepository, logger);
    }

    public static ViewFactory getInstance() {
        if(instance == null)
            instance = new ViewFactory();
        return instance;
    }

    public Object create(Class<?> controllerClass) {
        if(controllerClass == MainController.class)
            return new MainController(mainViewModel);

        throw new NullPointerException();
    }
}
