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

package com.dbychkov.words.app;

import android.app.Application;

import com.dbychkov.words.dagger.component.ApplicationComponent;
import com.dbychkov.words.dagger.component.DaggerApplicationComponent;
import com.dbychkov.words.dagger.module.ApplicationModule;
import com.dbychkov.words.util.LogHelper;

/**
 * Class for maintaining global application state
 */
public class App extends Application {

    private static final String TAG = LogHelper.makeLogTag(App.class);

    private static App singleton;

    public static App getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        setUncaughtExceptionHandler();
        initApplicationComponent();
    }

    private ApplicationComponent applicationComponent;

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder().applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                LogHelper.e(TAG, ex, "Exception escaped");
            }
        });
    }

}
