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

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import com.dbychkov.domain.Lesson;
import com.dbychkov.words.R;
import com.dbychkov.words.adapter.LessonsAdapter;
import com.dbychkov.words.presentation.LessonsPresenter;
import com.dbychkov.words.view.RenderLessonsView;
import com.dbychkov.words.widgets.RecyclerViewWithEmptyView;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

import java.util.List;

/**
 * Abstract fragment with lesson list
 */
public abstract class LessonsTabFragment extends BaseFragment implements RenderLessonsView {

    public static final int SPAN_COUNT = 2;
    protected View inflatedView;

    @Bind(R.id.list)
    RecyclerViewWithEmptyView recyclerView;

    @Bind(R.id.list_empty)
    TextView textView;

    @Bind(R.id.fragment_progressBar)
    ProgressBar progressBar;

    @BindString(R.string.errorText)
    String errorText;

    @Bind(R.id.scroll)
    NestedScrollView nestedScrollView;

    protected GridLayoutManager gridLayoutManager;

    public abstract LessonsAdapter getLessonsAdapter();

    public abstract LessonsPresenter getLessonsPresenter();

    public abstract String getEmptyMessage();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerViewWithAdapter();
        initPresenter();
    }

    private void initPresenter() {
        getLessonsPresenter().setView(this);
        getLessonsPresenter().initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLessonsPresenter().resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getLessonsPresenter().pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLessonsPresenter().destroy();
    }

    /**
     * Populate lesson list without initial animation
     */
    @Override
    public void renderLessonList(List<Lesson> lessonList) {
        getLessonsAdapter().disableAnimation();
        getLessonsAdapter().setItems(lessonList);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // List is populated with lessons at this point
                getLessonsAdapter().enableAnimation();
            }
        }, 350);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), errorText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_lessons, container, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    public void initRecyclerViewWithAdapter() {
        // Amount of columns
        gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Show placeholder when list doesn't contain any items
        recyclerView.setEmptyView(nestedScrollView);
        textView.setText(getEmptyMessage());

        // Populate list with items without animation
        recyclerView.setItemAnimator(new FadeInAnimator());
        getLessonsAdapter().disableAnimation();
        recyclerView.setAdapter(getLessonsAdapter());
        recyclerView.setHasFixedSize(false);
    }

}
