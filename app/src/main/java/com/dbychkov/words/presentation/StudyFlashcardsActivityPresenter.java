/**
 * Copyright (C) dbychkov.com.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dbychkov.words.presentation;

import android.os.Handler;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.view.StudyFlashcardsView;

import javax.inject.Inject;
import java.util.List;

/**
 * Presenter for {@link com.dbychkov.words.activity.StudyFlashcardsActivity}
 */
public class StudyFlashcardsActivityPresenter extends PresenterBase {

    public static final int DELAY_MILLIS = 1000;

    private FlashcardRepository flashcardRepository;
    private StudyFlashcardsView studyFlashcardsView;
    private Long lessonId;
    private List<Flashcard> unlearntFlashcards;
    private int currentPosition = 0;

    @Inject
    public StudyFlashcardsActivityPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            FlashcardRepository flashcardRepository) {
        super(threadExecutor, postExecutionThread);
        this.flashcardRepository = flashcardRepository;
    }

    public void setView(StudyFlashcardsView studyFlashcardsView) {
        this.studyFlashcardsView = studyFlashcardsView;
    }

    public void initialize(Long lessonId) {
        this.lessonId = lessonId;
        initWords();
    }

    private void initWords() {
        execute(flashcardRepository.getUnlearntFlashcardsFromLesson(lessonId),
                new DefaultSubscriber<List<Flashcard>>() {

                    @Override
                    public void onNext(List<Flashcard> unlearntFlashcards) {
                        StudyFlashcardsActivityPresenter.this.currentPosition = 0;
                        StudyFlashcardsActivityPresenter.this.unlearntFlashcards = unlearntFlashcards;
                        if (unlearntFlashcards.isEmpty()) {
                            showAllWordsAreLearntDialog();
                        } else {
                            showFlashcards(unlearntFlashcards);
                        }
                    }
                });
    }

    /**
     * Update "learnt" status of current flashcard and proceed to the next one
     */
    public void knowWordButtonPressed() {
        Flashcard currentFlashcard = unlearntFlashcards.get(currentPosition);
        currentFlashcard.setStatus(1);
        execute(flashcardRepository.updateFlashcard(currentFlashcard), new DefaultSubscriber<List<Flashcard>>() {
            @Override
            public void onNext(List<Flashcard> flashcards) {
                if (isLastFlashcard()) {
                    showLessonEndedDialog();
                } else {
                    showNextFlashcardImmediately();
                }
            }
        });
    }

    private boolean isLastFlashcard(){
        return currentPosition + 1 >= unlearntFlashcards.size();
    }

    /**
     * Show back of flashcard and proceed to the next flashcard
     */
    public void dontKnowWordButtonPressed() {
        boolean flashcardWasFlipped = studyFlashcardsView.showCardBack(currentPosition);
        boolean lastFlashcard = isLastFlashcard();
        if (flashcardWasFlipped) {
            showLessonEnded(lastFlashcard);
        } else {
            showLessonEndedDialog(lastFlashcard);
        }
    }

    private void showLessonEnded(boolean delayRequired){
        if (delayRequired) {
            showLessonEndedDialogWithDelay();
        } else {
            showNextFlashcardWithADelay();
        }
    }

    private void showLessonEndedDialog(boolean delayRequired){
        if (delayRequired) {
            showLessonEndedDialog();
        } else {
            showNextFlashcardImmediately();
        }
    }

    private void showNextFlashcardImmediately() {
        studyFlashcardsView.showFlashcard(++currentPosition);
    }

    private void showNextFlashcardWithADelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNextFlashcardImmediately();
            }
        }, DELAY_MILLIS);
    }

    private void showFlashcards(List<Flashcard> flashcards) {
        studyFlashcardsView.renderFlashcards(flashcards);
        studyFlashcardsView.showFlashcard(0);
    }

    private void showLessonEndedDialog() {
        studyFlashcardsView.showLessonEndedDialog();
    }

    private void showLessonEndedDialogWithDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLessonEndedDialog();
            }
        }, DELAY_MILLIS);
    }

    private void showAllWordsAreLearntDialog() {
        studyFlashcardsView.showAllWordsLearntDialog();
    }

}
