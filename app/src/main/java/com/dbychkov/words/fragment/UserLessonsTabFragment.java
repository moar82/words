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

package com.dbychkov.words.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindString;
import com.dbychkov.domain.Lesson;
import com.dbychkov.words.R;
import com.dbychkov.words.adapter.LessonsAdapter;
import com.dbychkov.words.adapter.UserLessonsAdapter;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.navigator.Navigator;
import com.dbychkov.words.presentation.LessonsPresenter;
import com.dbychkov.words.presentation.UserLessonsTabFragmentPresenter;
import com.dbychkov.words.view.RenderLessonsView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Tab with lessons created by user
 */
public class UserLessonsTabFragment extends LessonsTabFragment implements View.OnClickListener, RenderLessonsView {

    public static final int DELAY_MILLIS = 400;

    @Inject
    Navigator navigator;

    @Inject
    UserLessonsTabFragmentPresenter presenter;

    @Inject
    UserLessonsAdapter adapter;

    @BindString(R.string.user_lesson_name)
    String userLessonName;

    @BindString(R.string.noUserLessons)
    String emptyMessage;

    private FloatingActionButton fab;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    public static Fragment newInstance() {
        return new UserLessonsTabFragment();
    }

    @Override
    public LessonsAdapter getLessonsAdapter() {
        return adapter;
    }

    @Override
    public LessonsPresenter getLessonsPresenter() {
        return presenter;
    }

    @Override
    public String getEmptyMessage() {
        return emptyMessage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupFab();
        return view;
    }

    private void setupFab() {
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            presenter.createNewLessonButtonClicked();
        }
    }

    @Override
    public void renderCreatedLesson(Lesson lesson) {
        adapter.addFirst(lesson);
        gridLayoutManager.scrollToPosition(0);
        // Wait for lesson creation animation to complete, then open newly created lesson
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Lesson justCreatedLesson = getLessonsAdapter().getFirstItem();
                View sourceView = ((UserLessonsAdapter) getLessonsAdapter()).getFirstView();
                navigator.navigateToEditFlashcardsActivity(getActivity(), justCreatedLesson, sourceView);
                getActivity().overridePendingTransition(R.anim.appear, 0);
            }
        }, DELAY_MILLIS);
    }

    @Override
    public void renderLessonItemRemoved(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void renderLessonItemBookmarked(int position, boolean bookmarked) {
        throw new UnsupportedOperationException("Bookmarking not supported for user lessons");
    }

    @Override
    public void renderLessonList(List<Lesson> lessonList) {
        Collections.reverse(lessonList);
        super.renderLessonList(lessonList);
    }
}
