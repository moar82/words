package com.dbychkov.words.activity;

import com.dbychkov.domain.Flashcard;

import java.util.List;

public interface FlashcardsView {

    void renderFlashcards(List<Flashcard> flashcardList);

    void renderProgress(Integer renderProgress);
}
