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

package com.dbychkov.words.widgets;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dbychkov.words.R;

/**
 * Dedicated view which represents lesson
 */
public class LessonItemView extends CardView {

    private View inflatedView;

    public AnimatorSet set;

    @Bind(R.id.lesson_name)
    TextView lessonNameTextView;

    @Bind(R.id.lesson_image)
    ImageView lessonImageView;

    @Bind(R.id.bookmark_icon)
    ImageView bookmarkIcon;

    @Bind(R.id.remove_icon)
    ImageView removeIcon;

    public LessonItemView(Context context) {
        super(context);
        initView();
    }

    public LessonItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LessonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflatedView = inflater.inflate(R.layout.view_lesson, this, true);
        ButterKnife.bind(this, inflatedView);
        removeIcon.setColorFilter(Color.WHITE);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.lesson_animation);
        set.setTarget(this);
    }

    public void setBookmarkButtonListener(OnClickListener listener) {
        bookmarkIcon.setVisibility(VISIBLE);
        bookmarkIcon.setOnClickListener(listener);
    }

    public void setRemoveIconListener(OnClickListener listener) {
        removeIcon.setVisibility(VISIBLE);
        removeIcon.setOnClickListener(listener);
    }

    public void setBookmarked(boolean bookmarked) {
        if (bookmarked) {
            bookmarkIcon.setColorFilter(Color.YELLOW);
        } else {
            bookmarkIcon.setColorFilter(Color.WHITE);
        }
    }

    public void setLessonName(String lessonName) {
        lessonNameTextView.setText(lessonName);
    }

    public void setLessonImage(Bitmap bitmap) {
        lessonImageView.setImageBitmap(bitmap);
    }

    public void setOnLessonClickListener(OnClickListener listener) {
        lessonImageView.setOnClickListener(listener);
    }

    public ImageView getLessonImageView() {
        return lessonImageView;
    }

    public void restartAnimation() {
        set.cancel();
        set.start();
    }

}
