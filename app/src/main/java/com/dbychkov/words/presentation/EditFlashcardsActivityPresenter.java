package com.dbychkov.words.presentation;

import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.words.activity.EditFlashcardsView;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.SpeechService;

import javax.inject.Inject;
import java.util.List;

public class EditFlashcardsActivityPresenter extends FlashcardsActivityPresenter {

    private EditFlashcardsView editFlashcardsView;

    @Inject
    public EditFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        super(threadExecutor, postExecutionThread, flashcardRepository, speechService);
    }

    public void setView(EditFlashcardsView editFlashcardsView) {
        super.setView(editFlashcardsView);
        this.editFlashcardsView = editFlashcardsView;
    }

    public void showFlashCards(List<Flashcard> flashcards) {
        editFlashcardsView.renderFlashcards(flashcards);
    }

    public void removeFlashcard(final Flashcard flashcard, final int position) {
        execute(flashcardRepository.removeFlashcard(flashcard.getId()), new DefaultSubscriber<Void>() {

            @Override
            public void onNext(Void v) {
                editFlashcardsView.renderFlashcardRemoved(flashcard, position);

                execute(flashcardRepository.getFlashcardsFromLesson(flashcard.getLessonId()),
                        new DefaultSubscriber<List<Flashcard>>() {
                            @Override
                            public void onNext(List<Flashcard> flashcards) {
                                showFlashCards(flashcards);
                                showProgress(getProgressForWordList(flashcards));
                            }
                        });

            }
        });
    }

    public void onFlashcardRemoveClicked(Flashcard flashcard, int position) {
        editFlashcardsView.renderFlashcardRemovalSnackBar(flashcard, position);
    }
}
