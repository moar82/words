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
import com.dbychkov.words.activity.FlashcardsView;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.SpeechService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Base presenter for all
 */
public abstract class FlashcardsActivityPresenter extends AbstractPresenter {

    protected FlashcardRepository flashcardRepository;
    private Long lessonId;
    private SpeechService speechService;

    public FlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        super(threadExecutor, postExecutionThread);
        this.flashcardRepository = flashcardRepository;
        this.speechService = speechService;
    }

    public void initialize(Long lessonId) {
        this.lessonId = lessonId;
        initialize();
    }

    public void initialize() {
        execute(flashcardRepository.getFlashcardsFromLesson(lessonId), new DefaultSubscriber<List<Flashcard>>() {
            @Override
            public void onNext(List<Flashcard> flashcards) {
                FlashcardsActivityPresenter.this.showFlashCards(flashcards);
                FlashcardsActivityPresenter.this.showProgress(getProgressForWordList(flashcards));
            }
        });
    }

    private FlashcardsView flashcardsView;

    public void setView(FlashcardsView flashcardsView) {
        this.flashcardsView = flashcardsView;
    }

    public void showProgress(Integer progress){
        flashcardsView.renderProgress(progress);
    }

    abstract void showFlashCards(List<Flashcard> flashcards);

    public void clearProgressClicked() {
        flashcardRepository.clearProgressForLesson(lessonId)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Void>() {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        initialize();
                    }
                });

    }

    protected Integer getProgressForWordList(List<Flashcard> flashcardList) {
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

}
