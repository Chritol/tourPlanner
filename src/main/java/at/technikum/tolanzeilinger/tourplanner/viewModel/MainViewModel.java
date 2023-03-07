package at.technikum.tolanzeilinger.tourplanner.viewModel;

import at.technikum.tolanzeilinger.tourplanner.model.WordRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {

    private final ObservableList<String> names = FXCollections.observableArrayList();
    private final WordRepository wordRepository;
    private final StringProperty input = new SimpleStringProperty();

    private final StringProperty selectedName = new SimpleStringProperty();

    public MainViewModel(WordRepository wordRepository) {
        this.wordRepository = wordRepository;

        initializeView();
        initializeEventListeners();
    }

    private void initializeEventListeners() {
        wordRepository.addNewWordListener(this::addNewWord);
        wordRepository.addDeleteWordListener(this::removeWord);
    }

    private void initializeView() {
        names.addAll(wordRepository.findAll());
    }

    public String getInput() {
        return input.get();
    }

    public void setInput(String input) {
        this.input.set(input);
    }

    public StringProperty inputProperty() {
        return input;
    }

    public String getSelectedName() {
        return selectedName.get();
    }

    public StringProperty selectedNameProperty() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName.set(selectedName);
    }

    private void addNewWord(String word) {
        names.add(word);
    }

    private void removeWord(String word) {
        names.remove(word);
    }

    public ObservableList<String> getNames() {
        return names;
    }

    public void enterNewWord() {
        wordRepository.add(getInput());
        setInput("");
    }

    public void deleteWord() {
        wordRepository.remove(getSelectedName());
    }
}
