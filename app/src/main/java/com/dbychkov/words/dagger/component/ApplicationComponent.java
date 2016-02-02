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

import android.content.Context;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.activity.BaseActivity;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.dagger.module.ApplicationModule;
import com.dbychkov.words.data.LessonsImporter;
import com.dbychkov.words.navigator.Navigator;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.MarketService;
import com.dbychkov.words.util.ShareService;
import com.dbychkov.words.util.SpeechService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    Context getContext();

    LessonRepository provideLessonDataRepository();

    FlashcardRepository provideWordDataRepository();

    RxEventBus provideRxEventBus();

    MarketService provideMarketService();

    SpeechService provideSpeechService();

    ShareService provideShareService();

    LessonsImporter provideLessonsImporter();

    Navigator provideNavigator();
}
