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
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbychkov.domain.Lesson;
import com.dbychkov.words.R;
import com.dbychkov.words.activity.ViewFlashcardsActivity;
import com.dbychkov.words.navigator.Navigator;
import com.dbychkov.words.widgets.LessonItemView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for lessons
 */
public abstract class LessonsAdapter extends BaseListAdapter<Lesson, LessonItemView> {

    private static final boolean DEFAULT_ANIMATION_ENABLED = true;

    protected List<Lesson> lessons = new ArrayList<>();
    private boolean animationEnabled = DEFAULT_ANIMATION_ENABLED;
    protected Navigator navigator;

    public LessonsAdapter(Context context, Navigator navigator) {
        super(context);
        this.navigator = navigator;
    }

    public void enableAnimation() {
        animationEnabled = true;
    }

    public void disableAnimation() {
        animationEnabled = false;
    }

    @Override
    protected LessonItemView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (LessonItemView) LayoutInflater.from(context)
                .inflate(R.layout.adapter_item_lesson, viewGroup, false);
    }

    @Override
    protected void bind(final Lesson lesson, final LessonItemView view, BaseListAdapter.ViewHolder<LessonItemView> holder) {
        if (lesson != null){
            view.setLessonName(lesson.getLessonName());
            String imagePath = lesson.getImagePath();
            Picasso.with(context).load("file:///android_asset/" + imagePath)
                    .into(view.getLessonImageView());
            if (animationEnabled) {
                view.restartAnimation();
            }
            view.setOnLessonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLessonClicked(lesson, view);
                }
            });
        }
    }

    public void onLessonClicked(Lesson lesson, LessonItemView lessonItemView) {
        navigator.navigateToViewFlashcardsActivity(context, lesson, lessonItemView);
        ((Activity) context).overridePendingTransition(R.anim.appear, 0);
    }

}