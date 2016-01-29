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
import com.dbychkov.words.util.SpeechService;
import com.dbychkov.words.view.ViewEditFlashcardsView;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class ViewEditFlashcardsActivityPresenter extends AbstractPresenter {

    private FlashcardRepository flashcardRepository;
    private ViewEditFlashcardsView viewEditFlashcardsView;
    private Long lessonId;
    private Boolean editable;
    private SpeechService speechService;

    public ViewEditFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
                                               PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        super(threadExecutor, postExecutionThread);
        this.flashcardRepository = flashcardRepository;
        this.speechService = speechService;
    }

    public void setView(ViewEditFlashcardsView viewEditFlashcardsView) {
        this.viewEditFlashcardsView = viewEditFlashcardsView;
    }

    public void initialize(Long lessonId, Boolean editable) {
        this.lessonId = lessonId;
        this.editable = editable;
        initialize(editable);
    }

    private void initialize(final Boolean editable) {
        execute(flashcardRepository.getFlashcardsFromLesson(lessonId), new DefaultSubscriber<List<Flashcard>>() {

            @Override
            public void onNext(List<Flashcard> flashcards) {
                ViewEditFlashcardsActivityPresenter.this.showFlashCards(flashcards, editable);
                ViewEditFlashcardsActivityPresenter.this.showProgress(getProgressForWordList(flashcards));
            }
        });
    }

    public void removeFlashcard(final Flashcard flashcard, final int position) {
        execute(flashcardRepository.removeFlashcard(flashcard.getId()), new DefaultSubscriber<Void>() {

            @Override
            public void onNext(Void v) {
                viewEditFlashcardsView.renderRemovedFlashcard(flashcard, position);
            }
        });
    }



    private void showProgress(Integer progress){
        viewEditFlashcardsView.renderProgress(progress);
    }

    private void showFlashCards(List<Flashcard> flashcards, Boolean editable) {
        if (editable) {
            showEditableFlashcards(flashcards);
        } else {
            showReadOnlyFlashcards(flashcards);
        }
        setupFab(editable);
    }

    private void showReadOnlyFlashcards(List<Flashcard> flashcards) {
        viewEditFlashcardsView.renderReadOnlyFlashcards(flashcards);
    }


    private void showEditableFlashcards(List<Flashcard> flashcards) {
        viewEditFlashcardsView.renderEditableFlashcards(flashcards);
    }

    private void setupFab(Boolean editable){
        viewEditFlashcardsView.setupFab(editable);
    }

    public void clearProgressClicked() {
        flashcardRepository.clearProgressForLesson(lessonId)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Void>() {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        initialize(editable);
                    }
                });

    }

    private Integer getProgressForWordList(List<Flashcard> flashcardList) {
        int wordsLearnt = 0;
        for (Flashcard flashcard : flashcardList) {
            wordsLearnt += flashcard.getStatus();
        }
        int totalWords = flashcardList.size();
        return totalWords == 0 ? 0 : wordsLearnt * 100 / totalWords;
    }

    public void flashcardModified(Flashcard flashcard) {
        flashcardRepository.updateFlashcard(flashcard).subscribe();
    }

    public void onSpeakIconClicked(String word) {
            speechService.speak(word);
    }

    public void onFlashcardRemoveClicked(Flashcard flashcard, int position) {
        viewEditFlashcardsView.renderOnRemoveSnackBar(flashcard, position);
    }
}
