package com.dbychkov.words.view;

import com.dbychkov.domain.Flashcard;
import com.dbychkov.words.view.FlashcardsView;

/**
 * View capable of rendering list of flashcards and lesson progress
 */
public interface EditFlashcardsView extends FlashcardsView {

    void renderFlashcardRemoved(Flashcard flashcard, int position);

    void renderFlashcardRemovalSnackBar(Flashcard flashcard, final int position);

    void renderEditFlashcardDialog(Flashcard flashcard, final int position);

    void renderCreatedFlashcard(Flashcard insertedFlashcard);

}
