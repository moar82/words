/*
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

import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.bus.CreateBookmarkEvent;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Presenter for bookmarked lessons
 */
public class BookmarkedLessonsTabFragmentPresenter extends LessonsPresenter {

    @Inject
    public BookmarkedLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread,
            LessonRepository lessonRepository,
            RxEventBus rxEventBus) {
        super(threadExecutor, postExecutionThread, lessonRepository.getBookmarkedLessons(), rxEventBus);
        registerBookmarkedStateChangedEventListener();
    }

    private void registerBookmarkedStateChangedEventListener() {
        compositeSubscription.add(rxEventBus.toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object event) {
                if (event instanceof CreateBookmarkEvent) {
                    reloadLessonList();
                }
            }
        }));
    }

}


