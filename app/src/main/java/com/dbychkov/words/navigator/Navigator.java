/**
 * Copyright (C) dbychkov.
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

package com.dbychkov.words.navigator;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.dbychkov.domain.Lesson;
import com.dbychkov.words.activity.EditFlashcardsActivity;
import com.dbychkov.words.activity.LessonCatalogActivity;
import com.dbychkov.words.activity.StudyFlashcardsActivity;
import com.dbychkov.words.activity.ViewFlashcardsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class which knows to navigate between activities
 */
@Singleton
public class Navigator {

    @Inject
    public void Navigator() {
    }

    /**
     * Navigate to main activity with a list of lessons
     */
    public void navigateToMainActivity(Context context) {
        Intent intent = new Intent(context, LessonCatalogActivity.class);
        context.startActivity(intent);
    }

    /**
     * Navigate to edit flashcards activity. {@code view} dimensions is a starting point for reveal effect.
     */
    public void navigateToEditFlashcardsActivity(Context context, Lesson lesson, View view) {
        Intent intent = EditFlashcardsActivity.createIntent(context, lesson, view);
        context.startActivity(intent);
    }

    /**
     * Navigate to view flashcards activity. {@code view} dimensions is a starting point for reveal effect.
     */
    public void navigateToViewFlashcardsActivity(Context context, Lesson lesson, View view) {
        Intent intent = ViewFlashcardsActivity.createIntent(context, lesson, view);
        context.startActivity(intent);
    }

    /**
     * Navigate to study flashcards activity
     */
    public void navigateToStudyFlashcardsActivity(Context context, Long lessonId) {
        Intent intent = StudyFlashcardsActivity.createIntent(context, lessonId);
        context.startActivity(intent);
    }

}
