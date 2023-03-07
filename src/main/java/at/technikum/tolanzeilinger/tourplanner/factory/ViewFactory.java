package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.model.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.view.MainController;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;

public class ViewFactory {
    private static ViewFactory instance;

    // Repositories
    private final WordRepository wordRepository;
    private final MainViewModel mainViewModel;

    private ViewFactory() {
        // initialize Repositories
        this.wordRepository = new WordRepository();

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(wordRepository);
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
