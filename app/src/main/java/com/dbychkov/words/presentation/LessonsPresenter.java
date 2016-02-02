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

import com.dbychkov.domain.Lesson;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.view.RenderLessonsView;
import rx.Observable;

import java.util.List;

/**
 * Abstract presenter for a list of lessons
 */
public abstract class LessonsPresenter extends PresenterBase {

    protected RenderLessonsView renderLessonsView;
    protected Observable<List<Lesson>> lessonsObservable;
    protected RxEventBus rxEventBus;

    public LessonsPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            Observable<List<Lesson>> lessonsObservable, RxEventBus rxEventBus) {
        super(threadExecutor, postExecutionThread);
        this.lessonsObservable = lessonsObservable;
        this.rxEventBus = rxEventBus;
    }

    public void setView(RenderLessonsView renderLessonsView) {
        this.renderLessonsView = renderLessonsView;
    }

    public void initialize() {
        reloadLessonList();
    }

    protected void reloadLessonList() {
        showLoading();
        execute(lessonsObservable, new DefaultSubscriber<List<Lesson>>() {

            @Override
            public void onCompleted() {
                LessonsPresenter.this.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                LessonsPresenter.this.hideLoading();
                LessonsPresenter.this.showError();
            }

            @Override
            public void onNext(List<Lesson> lessons) {
                LessonsPresenter.this.showLessonsList(lessons);
            }
        });
    }

    private void showLoading() {
        renderLessonsView.showLoading();
    }

    private void hideLoading() {
        renderLessonsView.hideLoading();
    }

    private void showError() {
        renderLessonsView.showError("Error");
    }

    private void showLessonsList(List<Lesson> lessons) {
        renderLessonsView.renderLessonList(lessons);
    }

}
