package com.dbychkov.words.activity;

import com.dbychkov.domain.Flashcard;

/**
 * View capable of rendering list of flashcards and lesson progress
 */
public interface EditFlashcardsView extends FlashcardsView {

    void renderFlashcardRemoved(Flashcard flashcard, int position);

    void renderFlashcardRemovalSnackBar(Flashcard flashcard, final int position);

}
