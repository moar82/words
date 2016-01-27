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
import com.dbychkov.domain.Lesson;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.activity.ViewEditFlashcardsActivity;
import com.dbychkov.words.widgets.LessonItemView;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for displaying user lessons
 */
public class UserLessonsAdapter extends LessonsAdapter {

    private View firstView;

    private LessonRepository lessonRepository;

    @Inject
    public UserLessonsAdapter(Activity activity, LessonRepository lessonRepository) {
        super(activity);
        this.lessonRepository = lessonRepository;
    }

    @Override
    protected void bind(final Lesson lesson, final LessonItemView view,
            final BaseListAdapter.ViewHolder<LessonItemView> holder) {
        super.bind(lesson, view, holder);
        if (holder.getAdapterPosition() == 0) {
            firstView = view;
        }
        view.setRemoveIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessonRepository.removeLessonById(lesson.getId()).subscribe();
                removeItem(holder.getAdapterPosition());
            }
        });
    }

    private List<Lesson> sortLessonsByCreationDay(List<Lesson> lessons) {
        Collections.sort(lessons, new Comparator<Lesson>() {
            @Override
            public int compare(Lesson lhs, Lesson rhs) {
                return (int) (rhs.getId() - lhs.getId());
            }
        });
        return lessons;
    }

    public View getFirstView() {
        return firstView;
    }

    @Override
    public void onLessonClicked(Lesson lesson, LessonItemView lessonItemView) {
        ViewEditFlashcardsActivity
                .startActivity(lesson, lessonItemView, context, ViewEditFlashcardsActivity.class, true);
    }
}
