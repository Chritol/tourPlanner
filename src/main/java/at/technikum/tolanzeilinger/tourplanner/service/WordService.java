package at.technikum.tolanzeilinger.tourplanner.service;

import at.technikum.tolanzeilinger.tourplanner.model.repositories.WordRepository;

public class WordService {

    private WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }


}
