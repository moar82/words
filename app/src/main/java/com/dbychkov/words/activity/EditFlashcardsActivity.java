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
import android.view.View;

import com.dbychkov.domain.Lesson;
import com.dbychkov.words.R;

public class EditFlashcardsActivity extends AbstractFlashcardsActivity {

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
}

