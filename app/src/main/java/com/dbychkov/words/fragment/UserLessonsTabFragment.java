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

import com.dbychkov.domain.Lesson;
import com.dbychkov.words.R;
import com.dbychkov.words.activity.ViewEditFlashcardsActivity;
import com.dbychkov.words.adapter.LessonsAdapter;
import com.dbychkov.words.adapter.UserLessonsAdapter;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.presentation.LessonsPresenter;
import com.dbychkov.words.presentation.UserLessonsTabFragmentPresenter;
import com.dbychkov.words.view.RenderLessonsView;

import javax.inject.Inject;

import butterknife.BindString;

public class UserLessonsTabFragment extends LessonsTabFragment implements View.OnClickListener, RenderLessonsView {

    @Inject
    UserLessonsTabFragmentPresenter userLessonsTabFragmentPresenter;
    @Inject
    UserLessonsAdapter userLessonsAdapter;
    @BindString(R.string.user_lesson_name)
    String userLessonName;
    @BindString(R.string.noUserLessons)
    String emptyMessage;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userLessonsTabFragmentPresenter.setView(this);
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    private FloatingActionButton fab;

    public static Fragment newInstance() {
        return new UserLessonsTabFragment();
    }

    @Override
    public LessonsAdapter getLessonsAdapter() {
        return userLessonsAdapter;
    }

    @Override
    public LessonsPresenter getLessonsPresenter() {
        return userLessonsTabFragmentPresenter;
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
            userLessonsTabFragmentPresenter.createNewLessonButtonClicked();
        }
    }

    @Override
    public void renderCreatedLesson(Lesson lesson) {
        userLessonsAdapter.addFirst(lesson);
        gridLayoutManager.scrollToPosition(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewEditFlashcardsActivity
                        .startActivity(getLessonsAdapter().getFirstItem(),
                                ((UserLessonsAdapter) getLessonsAdapter()).getFirstView(),
                                getActivity(), ViewEditFlashcardsActivity.class, true);
            }
        }, 400);
    }
}
