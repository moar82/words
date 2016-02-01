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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.Lesson;
import com.dbychkov.words.R;
import com.dbychkov.words.adapter.EditFlashcardsAdapter;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.presentation.EditFlashcardsActivityPresenter;
import com.dbychkov.words.util.SpeechService;
import rx.functions.Action1;

import javax.inject.Inject;
import java.util.List;

public class EditFlashcardsActivity extends FlashcardsActivity implements EditFlashcardsView{

    private LinearLayoutManager linearLayoutManager;

    @Inject
    EditFlashcardsAdapter editFlashcardsAdapter;

    @Inject
    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter;


    @Override
    public void onResume() {
        super.onResume();
        editFlashcardsActivityPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        editFlashcardsActivityPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editFlashcardsActivityPresenter.destroy();
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Inject
    public SpeechService speechService;

    public void renderFlashcards(List<Flashcard> flashcardList) {
        editFlashcardsAdapter.setItems(flashcardList);
        recyclerView.setAdapter(editFlashcardsAdapter);
    }

    @Override
    void clearProgressClicked() {
        editFlashcardsActivityPresenter.clearProgressClicked();
    }

    @Override
    public void onCreateExpandingActivity(Bundle savedInstanceState) {
        super.onCreateExpandingActivity(savedInstanceState);
        initRecyclerView();
        initPresenter();
        setupFab();
    }

    private void initPresenter() {
        editFlashcardsActivityPresenter.setView(this);
        editFlashcardsActivityPresenter.initialize(lessonId);
    }

    public static void startActivity(Lesson lesson, View view, Context context) {
        Intent intent = new Intent(context, EditFlashcardsActivity.class);
        intent.putExtra(EXTRA_LESSON_ID, lesson.getId());
        intent.putExtra(EXTRA_LESSON_IMAGE_PATH, lesson.getImagePath());
        intent.putExtra(EXTRA_LESSON_NAME, lesson.getLessonName());
        intent.putExtra(EXTRA_EDITABLE_LESSON, true);
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        intent.putExtra(EXTRA_PROPERTY_TOP, screenLocation[1]);
        intent.putExtra(EXTRA_PROPERTY_LEFT, screenLocation[0]);
        intent.putExtra(EXTRA_PROPERTY_WIDTH, view.getWidth());
        intent.putExtra(EXTRA_PROPERTY_HEIGHT, view.getHeight());
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.appear, 0);
    }

    public void setupFab() {
        FloatingActionButton fab = null;
//        if (editable) {
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Flashcard flashcard = new Flashcard();
                    flashcard.setLessonId(lessonId);
                    flashcard.setWord("Flashcard");
                    flashcard.setDefinition("Definition");
                    flashcardRepository.addFlashcard(flashcard).subscribe(new Action1<Flashcard>() {
                        @Override
                        public void call(final Flashcard insertedFlashcard) {
                            if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    public void onScrollStateChanged(RecyclerView rv, int state) {
                                        if (state == RecyclerView.SCROLL_STATE_IDLE) {
                                            addToTop(insertedFlashcard);
                                            rv.removeOnScrollListener(this);
                                        }
                                    }
                                });
                                recyclerView.smoothScrollToPosition(0);
                            } else {
                                addToTop(insertedFlashcard);
                            }
                        }
                    });
                }
            });
//        } else {
//            fab = (FloatingActionButton) findViewById(R.id.fab);
//            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
//            p.setBehavior(null);
//            p.setAnchorId(View.NO_ID);
//            fab.setLayoutParams(p);
//            fab.setVisibility(View.GONE);
//        }
    }

    private void addToTop(Flashcard flashcard) {
        editFlashcardsAdapter.addFirst(flashcard);
        recyclerView.scrollToPosition(0);
    }

    private void initRecyclerView() {
            recyclerView.setEmptyView(nestedScrollView);
            textView.setText("No user cards");
            recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
    }

    @Override
    public void renderFlashcardRemovalSnackBar(final Flashcard flashcard, final int position) {
        Snackbar
                .make(getRootLayout(),
                        "Remove card?",
                        Snackbar.LENGTH_LONG)
                .setAction("REMOVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editFlashcardsActivityPresenter.removeFlashcard(flashcard, position);
                    }
                })
                .show();
    }

    @Override
    public void renderFlashcardRemoved(Flashcard flashcard, int position) {
        editFlashcardsAdapter.removeItem(position);
    }



}

