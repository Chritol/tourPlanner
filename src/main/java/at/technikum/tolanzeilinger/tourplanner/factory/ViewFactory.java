package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
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

    private ViewFactory() {
        this.eventAggregator = new EventAggregator();

        // initialize Repositories
        this.wordRepository = new WordRepository(eventAggregator);

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(eventAggregator, wordRepository);
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
