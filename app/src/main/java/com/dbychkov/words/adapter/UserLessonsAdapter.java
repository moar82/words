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
import com.dbychkov.words.activity.EditFlashcardsActivity;
import com.dbychkov.words.presentation.UserLessonsTabFragmentPresenter;
import com.dbychkov.words.widgets.LessonItemView;

import javax.inject.Inject;

/**
 * Adapter for displaying user lessons
 */
public class UserLessonsAdapter extends LessonsAdapter {

    private View firstView;

    private UserLessonsTabFragmentPresenter presenter;

    @Inject
    public UserLessonsAdapter(Activity activity, UserLessonsTabFragmentPresenter presenter) {
        super(activity);
        this.presenter = presenter;
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
                presenter.lessonItemClicked(lesson.getId(), holder.getAdapterPosition());
            }
        });
    }

    public View getFirstView() {
        return firstView;
    }

    @Override
    public void onLessonClicked(Lesson lesson, LessonItemView lessonItemView) {
        EditFlashcardsActivity.startActivity(lesson, lessonItemView, context);
    }
}
