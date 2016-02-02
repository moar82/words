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
import com.dbychkov.words.view.ListFlashcardsView;

import java.util.List;

import javax.inject.Inject;

public class ListFlashcardsActivityPresenter extends PresenterBase {

    private FlashcardRepository flashcardRepository;
    private ListFlashcardsView listFlashcardsView;

    @Inject
    public ListFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository) {
        super(threadExecutor, postExecutionThread);
        this.flashcardRepository = flashcardRepository;
    }

    public void setView(ListFlashcardsView listFlashcardsView) {
        this.listFlashcardsView = listFlashcardsView;
    }

    public void initialize(Long lessonId) {
        execute(flashcardRepository.getFlashcardsFromLesson(lessonId), new DefaultSubscriber<List<Flashcard>>() {

            @Override
            public void onCompleted() {
                ListFlashcardsActivityPresenter.this.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                ListFlashcardsActivityPresenter.this.hideLoading();
                ListFlashcardsActivityPresenter.this.showError();
            }

            @Override
            public void onNext(List<Flashcard> flashcards) {
                ListFlashcardsActivityPresenter.this.showWordList(flashcards);
            }
        });
    }

    private void hideLoading() {

    }

    private void showError() {

    }

    private void showWordList(List<Flashcard> flashcardList) {
        listFlashcardsView.renderWordList(flashcardList);
    }


}
