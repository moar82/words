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

package com.dbychkov.words.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.Lesson;
import com.dbychkov.words.adapter.ViewFlashcardsAdapter;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.presentation.ViewFlashcardsActivityPresenter;
import com.dbychkov.words.view.ViewFlashcardsView;

import javax.inject.Inject;
import java.util.List;

/**
 * Activity with a list of flashcards
 */
public class ViewFlashcardsActivity extends FlashcardsActivity implements ViewFlashcardsView {

    @Inject
    ViewFlashcardsAdapter viewFlashcardsAdapter;

    @Inject
    ViewFlashcardsActivityPresenter viewFlashcardsActivityPresenter;

    @Override
    public void onResume() {
        super.onResume();
        viewFlashcardsActivityPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewFlashcardsActivityPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewFlashcardsActivityPresenter.destroy();
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    private void initPresenter() {
        viewFlashcardsActivityPresenter.setView(this);
        viewFlashcardsActivityPresenter.initialize(lessonId);
    }

    @Override
    public void onCreateExpandingActivity(Bundle savedInstanceState) {
        super.onCreateExpandingActivity(savedInstanceState);
        initRecyclerView();
        initPresenter();
    }

    public void renderFlashcards(List<Flashcard> flashcardList) {
        viewFlashcardsAdapter.setItems(flashcardList);
        recyclerView.setAdapter(viewFlashcardsAdapter);
    }

    public static Intent createIntent(Context context, Lesson lesson, View view) {
        Intent intent = new Intent(context, ViewFlashcardsActivity.class);
        intent.putExtra(EXTRA_LESSON_ID, lesson.getId());
        intent.putExtra(EXTRA_LESSON_IMAGE_PATH, lesson.getImagePath());
        intent.putExtra(EXTRA_LESSON_NAME, lesson.getLessonName());
        intent.putExtra(EXTRA_EDITABLE_LESSON, false);
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        intent.putExtra(EXTRA_PROPERTY_TOP, screenLocation[1]);
        intent.putExtra(EXTRA_PROPERTY_LEFT, screenLocation[0]);
        intent.putExtra(EXTRA_PROPERTY_WIDTH, view.getWidth());
        intent.putExtra(EXTRA_PROPERTY_HEIGHT, view.getHeight());
        return intent;
    }

    private void initRecyclerView() {
        recyclerView.setEmptyView(nestedScrollView);
        textView.setText("No cards");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    void clearProgressClicked() {
        viewFlashcardsActivityPresenter.clearProgressClicked();
    }
}

