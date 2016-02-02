package com.dbychkov.words.view;

import com.dbychkov.domain.Flashcard;

import java.util.List;

/**
 * Flashcards view
 */
public interface FlashcardsView {

    void renderFlashcards(List<Flashcard> flashcardList);

    void renderProgress(Integer renderProgress);
}
