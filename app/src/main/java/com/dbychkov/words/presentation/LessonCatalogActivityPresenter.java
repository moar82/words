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

package com.dbychkov.words.presentation;

import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.view.LessonCatalogView;

import javax.inject.Inject;

/**
 * Presenter for {@link com.dbychkov.words.activity.LessonCatalogActivity}
 */
public class LessonCatalogActivityPresenter extends PresenterBase {

    private LessonCatalogView lessonCatalogView;

    @Inject
    public LessonCatalogActivityPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    public void setView(LessonCatalogView lessonCatalogView) {
        this.lessonCatalogView = lessonCatalogView;
    }

    public void initialize() {
    }

    public void rateButtonClicked() {
        lessonCatalogView.renderRateScreen();
    }

    public void shareButtonClicked() {
        lessonCatalogView.renderShareScreen();
    }

    public void aboutButtonClicked() {
        lessonCatalogView.renderAboutScreen();
    }
}
