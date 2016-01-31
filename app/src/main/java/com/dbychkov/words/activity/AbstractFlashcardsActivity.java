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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.words.R;
import com.dbychkov.words.adapter.EditFlashcardsAdapter;
import com.dbychkov.words.adapter.ViewFlashcardsAdapter;
import com.dbychkov.words.presentation.ViewEditFlashcardsActivityPresenter;
import com.dbychkov.words.view.ViewEditFlashcardsView;
import com.dbychkov.words.widgets.RecyclerViewWithEmptyView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public abstract class AbstractFlashcardsActivity extends AbstractExpandingActivity
        implements AppBarLayout.OnOffsetChangedListener, ViewEditFlashcardsView {

    public static final String EXTRA_LESSON_ID = "lessonId";
    public static final String EXTRA_LESSON_NAME = "lessonName";
    public static final String EXTRA_LESSON_IMAGE_PATH = "imagePath";
    public static final String EXTRA_EDITABLE_LESSON = "isLessonEditable";

    boolean editableList = false;


    @Inject
    FlashcardRepository flashcardRepository;

    @Inject
    ViewEditFlashcardsActivityPresenter viewEditFlashcardsActivityPresenter;

    @Bind(R.id.main_content)
    CoordinatorLayout rootCoordinatorLayout;

    @Bind(R.id.test_cards_button)
    ImageView testCardsButton;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.header_image)
    ImageView headerImage;

    @Bind(R.id.lesson_progress)
    NumberProgressBar numberProgressBar;

    @Bind(R.id.list)
    RecyclerViewWithEmptyView recyclerView;

    @Bind(R.id.list_empty)
    TextView textView;

    @Bind(R.id.scroll)
    NestedScrollView nestedScrollView;

    private LinearLayoutManager linearLayoutManager;

    private int testCardsButtonHeight;

    private int testCardsButtonWidth;

    protected long lessonId;

    protected String lessonImagePath;

    protected String lessonName;

    @Override
    public void onResume() {
        super.onResume();
        viewEditFlashcardsActivityPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewEditFlashcardsActivityPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewEditFlashcardsActivityPresenter.destroy();
    }


//    public static void startActivity(Lesson lesson, View view, Context context,
//                                     Class<? extends Activity> activityClass, boolean editableLesson) {
//        Intent intent = new Intent(context, activityClass);
//        intent.putExtra(EXTRA_LESSON_ID, lesson.getId());
//        intent.putExtra(EXTRA_LESSON_IMAGE_PATH, lesson.getImagePath());
//        intent.putExtra(EXTRA_LESSON_NAME, lesson.getLessonName());
//        intent.putExtra(EXTRA_EDITABLE_LESSON, editableLesson);
//        int[] screenLocation = new int[2];
//        view.getLocationOnScreen(screenLocation);
//        intent.putExtra(EXTRA_PROPERTY_TOP, screenLocation[1]);
//        intent.putExtra(EXTRA_PROPERTY_LEFT, screenLocation[0]);
//        intent.putExtra(EXTRA_PROPERTY_WIDTH, view.getWidth());
//        intent.putExtra(EXTRA_PROPERTY_HEIGHT, view.getHeight());
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.appear, 0);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_cards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                viewEditFlashcardsActivityPresenter.clearProgressClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initTitle() {
        collapsingToolbar.setTitle(lessonName);
    }

    private void initExtra() {
        lessonId = getIntent().getLongExtra(EXTRA_LESSON_ID, -1);
        lessonImagePath = getIntent().getStringExtra(EXTRA_LESSON_IMAGE_PATH);
        lessonName = getIntent().getStringExtra(EXTRA_LESSON_NAME);
        editableList = getIntent().getBooleanExtra(EXTRA_EDITABLE_LESSON, false);
    }

    @OnClick(R.id.test_cards_button)
    void proceedToExercises() {
        Intent intent = new Intent(AbstractFlashcardsActivity.this, StudyFlashcardsActivity.class);
        intent.putExtra(EXTRA_LESSON_ID, lessonId);
        intent.putExtra(EXTRA_LESSON_IMAGE_PATH, lessonImagePath);
        intent.putExtra(EXTRA_LESSON_NAME, lessonName);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right_100, R.anim.slide_out_left_100);
    }

    private void initHeader() {
        Picasso
                .with(this)
                .load("file:///android_asset/" + lessonImagePath)
                .into(headerImage);
        testCardsButton.setColorFilter(getResources().getColor(R.color.colorAccent));
        testCardsButtonWidth = testCardsButton.getLayoutParams().width;
        testCardsButtonHeight = testCardsButton.getLayoutParams().height;

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public View getRootLayout() {
        return rootCoordinatorLayout;
    }

    @Override
    public void onCreateExpandingActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cards);
        ButterKnife.bind(this);
        initExtra();
        initTitle();
        initHeader();
        initRecyclerView();
        initPresenter();
    }

    private void initPresenter() {
        viewEditFlashcardsActivityPresenter.setView(this);
        viewEditFlashcardsActivityPresenter.initialize(lessonId, editableList);
    }

    @Override
    public void setupFab(Boolean editable) {
        FloatingActionButton fab = null;
        if (editable) {
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
        } else {
            fab = (FloatingActionButton) findViewById(R.id.fab);
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            p.setBehavior(null);
            p.setAnchorId(View.NO_ID);
            fab.setLayoutParams(p);
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderProgress(Integer renderProgress) {
        numberProgressBar.setProgress(renderProgress);
    }

    private void addToTop(Flashcard flashcard) {
        editFlashcardsAdapter.addFirst(flashcard);
        recyclerView.scrollToPosition(0);
    }

    private void initRecyclerView() {
        if (editableList) {
            recyclerView.setEmptyView(nestedScrollView);
            textView.setText("No user cards");
            recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        } else {
            recyclerView.setEmptyView(nestedScrollView);
            textView.setText("No cards");
            recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float progressPercentage = (float) Math.abs(offset) / (float) maxScroll;
        testCardsButton.setAlpha(1 - progressPercentage);
        ViewGroup.LayoutParams layoutParams = testCardsButton.getLayoutParams();
        layoutParams.width = (int) (testCardsButtonWidth * (1 - progressPercentage));
        layoutParams.height = (int) (testCardsButtonHeight * (1 - progressPercentage));
        testCardsButton.setLayoutParams(layoutParams);
    }


    @Inject
    ViewFlashcardsAdapter viewFlashcardsAdapter;

    @Inject
    EditFlashcardsAdapter editFlashcardsAdapter;

    @Override
    public void renderReadOnlyFlashcards(List<Flashcard> flashcardList) {
        viewFlashcardsAdapter.setItems(flashcardList);
        recyclerView.setAdapter(viewFlashcardsAdapter);
    }

    @Override
    public void renderEditableFlashcards(List<Flashcard> flashcardList) {
        editFlashcardsAdapter.setItems(flashcardList);
        recyclerView.setAdapter(editFlashcardsAdapter);
    }

    @Override
    public void renderOnRemoveSnackBar(final Flashcard flashcard, final int position) {
        Snackbar
                .make(getRootLayout(),
                        "Remove card?",
                        Snackbar.LENGTH_LONG)
                .setAction("REMOVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewEditFlashcardsActivityPresenter.removeFlashcard(flashcard, position);
                    }
                })
                .show();
    }


    @Override
    public void renderRemovedFlashcard(Flashcard flashcard, int position) {
        editFlashcardsAdapter.removeItem(position);
    }


}

