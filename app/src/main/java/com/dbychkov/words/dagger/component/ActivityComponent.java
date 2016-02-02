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

package com.dbychkov.words.dagger.component;

import android.app.Activity;
import com.dbychkov.words.activity.*;
import com.dbychkov.words.adapter.ViewFlashcardsAdapter;
import com.dbychkov.words.dagger.PerActivity;
import com.dbychkov.words.dagger.module.ActivityModule;
import com.dbychkov.words.fragment.*;
import com.dbychkov.words.presentation.*;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BundledLessonsTabFragment bundledLessonsTabFragment);

    void inject(BookmarkedLessonsTabFragment bookmarkedLessonsTabFragment);

    void inject(UserLessonsTabFragment userLessonsTabFragment);

    void inject(CardContainerFragment cardContainerFragment);

    void inject(CardFragment cardFragment);

    void inject(LessonCatalogActivity lessonCatalogActivity);

    void inject(StudyFlashcardsActivity studyFlashcardsActivity);

    void inject(EditFlashcardsActivity editFlashcardsActivity);

    void inject(ViewFlashcardsActivity viewFlashcardsActivity);

    void inject(ViewFlashcardsAdapter viewFlashcardsAdapter);

    void inject(SplashActivity splashActivity);

    Activity activity();

    UserLessonsTabFragmentPresenter userLessonsTabFragmentPresenter();

    BundledLessonsTabFragmentPresenter bundledLessonsTabFragmentPresenter();

    BookmarkedLessonsTabFragmentPresenter bookmarkedLessonsTabFragmentPresenter();

    ViewFlashcardsActivityPresenter viewFlashcardsActivityPresenter();

    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter();
}