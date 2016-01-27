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

package com.dbychkov.words.adapter;

import android.app.Activity;
import android.view.View;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.Lesson;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.bus.CreateBookmarkEvent;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.widgets.EditableFlashcardView;
import com.dbychkov.words.widgets.LessonItemView;
import rx.functions.Action1;

import javax.inject.Inject;

/**
 * Adapter responsible for rendering lessons which were installed with the application
 */
public class BundledLessonsAdapter extends LessonsAdapter {

    private LessonRepository lessonRepository;

    private RxEventBus rxEventBus;

    @Inject
    public BundledLessonsAdapter(Activity activity, LessonRepository lessonRepository,
                                 RxEventBus rxEventBus) {
        super(activity);
        this.lessonRepository = lessonRepository;
        this.rxEventBus = rxEventBus;
    }

    @Override
    protected void bind(final Lesson lesson, final LessonItemView view, final BaseListAdapter.ViewHolder<LessonItemView> holder) {
        super.bind(lesson, view, holder);
        view.setBookmarked(lesson.isBookmarked());
        view.setBookmarkButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update bookmarked state in database
                lessonRepository.bookmarkLesson(lesson.getId()).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        view.setBookmarked(aBoolean);
                    }
                });
                // Send event to adjacent tabs
                rxEventBus.send(new CreateBookmarkEvent());
            }
        });
    }
}
