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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dbychkov.words.app.App;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.dagger.component.ApplicationComponent;
import com.dbychkov.words.dagger.component.DaggerActivityComponent;
import com.dbychkov.words.dagger.module.ActivityModule;

/**
 * Base activity for all the activities in the app
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        injectActivity(getActivityComponent());
    }

    public ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private void initActivityComponent() {
        this.activityComponent = DaggerActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    public abstract void injectActivity(ActivityComponent component);
}
