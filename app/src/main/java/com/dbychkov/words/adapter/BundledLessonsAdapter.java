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
import com.dbychkov.words.navigator.Navigator;
import com.dbychkov.words.presentation.BundledLessonsTabFragmentPresenter;
import com.dbychkov.words.widgets.LessonItemView;

import javax.inject.Inject;

/**
 * Adapter responsible for rendering lessons which were installed with the application
 */
public class BundledLessonsAdapter extends LessonsAdapter {

    private BundledLessonsTabFragmentPresenter presenter;

    @Inject
    public BundledLessonsAdapter(Activity activity, BundledLessonsTabFragmentPresenter presenter, Navigator navigator) {
        super(activity, navigator);
        this.presenter = presenter;
    }

    @Override
    protected void bind(final Lesson lesson, LessonItemView view,
            final BaseListAdapter.ViewHolder<LessonItemView> holder) {
        super.bind(lesson, view, holder);
        view.setBookmarked(lesson.isBookmarked());
        view.setBookmarkButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.bookmarkedLessonClicked(lesson.getId(), holder.getAdapterPosition());
            }
        });
    }
}
