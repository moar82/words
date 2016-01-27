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

import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.view.StudyFlashcardsView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StudyFlashcardsActivityPresenter extends AbstractPresenter {

    private FlashcardRepository flashcardRepository;
    private StudyFlashcardsView studyFlashcardsView;
    private Long lessonId;
    private List<Flashcard> unlearntFlashcards;
    private int currentPosition = 0;

    @Inject
    public StudyFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository) {
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

        execute(flashcardRepository.getFlashcardsFromLesson(lessonId), new DefaultSubscriber<List<Flashcard>>() {

            @Override
            public void onCompleted() {
                StudyFlashcardsActivityPresenter.this.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                StudyFlashcardsActivityPresenter.this.hideLoading();
                StudyFlashcardsActivityPresenter.this.showError();
            }

            @Override
            public void onNext(List<Flashcard> flashcards) {
                currentPosition = 0;
                unlearntFlashcards = new ArrayList<>();
                for (Flashcard flashcard : flashcards) {
                    if (flashcard.getStatus() == 0) {
                        unlearntFlashcards.add(flashcard);
                    }
                }
                if (unlearntFlashcards.isEmpty()) {
                    showAllWordsAreLearntDialog();
                } else {
                    showFlashcards(unlearntFlashcards);
                }

            }
        });

    }

    public void knowWordButtonPressed(){
        Flashcard currentFlashcard = unlearntFlashcards.get(currentPosition);
        currentFlashcard.setStatus(1);
        execute(flashcardRepository.updateFlashcard(currentFlashcard), new DefaultSubscriber<List<Flashcard>>() {
            @Override
            public void onNext(List<Flashcard> flashcards) {
                if (++currentPosition >= unlearntFlashcards.size()){
                    showLessonEndedDialog();
                } else {
                    studyFlashcardsView.showFlashcard(currentPosition);
                }
            }
        });
    }

    public void dontKnowWordButtonPressed(){
        studyFlashcardsView.showFlashcard(++currentPosition);
    }

    private void showFlashcards(List<Flashcard> flashcards) {
        studyFlashcardsView.renderFlashcards(flashcards);
        studyFlashcardsView.showFlashcard(0);
    }

    private void showLessonEndedDialog() {
        studyFlashcardsView.showLessonEndedDialog();
    }

    private void showAllWordsAreLearntDialog(){
        studyFlashcardsView.showAllWordsLearntDialog();
    }

    private void hideLoading() {

    }

    private void showError() {

    }

}
