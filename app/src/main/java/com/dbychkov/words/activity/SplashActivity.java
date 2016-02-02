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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dbychkov.words.R;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.data.LessonsImporter;
import com.dbychkov.words.navigator.Navigator;
import com.dbychkov.words.presentation.SplashActivityPresenter;
import com.dbychkov.words.util.LogHelper;
import com.dbychkov.words.view.SplashView;

import javax.inject.Inject;

/**
 * Splash screen activity. Flashcards database is imported during first launch.
 */
public class SplashActivity extends BaseActivity implements SplashView {

    private static final String TAG = LogHelper.makeLogTag(SplashActivity.class);

    @Bind(R.id.import_progress_bar)
    NumberProgressBar importProgressBar;

    @Bind(R.id.importing_text_view)
    TextView importingTextView;

    @Inject
    SplashActivityPresenter splashActivityPresenter;

    @Inject
    LessonsImporter lessonsImporter;

    @Inject
    Navigator navigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initPresenter();
    }

    private void initPresenter(){
        splashActivityPresenter.setView(this);
        splashActivityPresenter.initialize();
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void showLoading() {
        importingTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderSplashScreenEnded() {
        navigator.navigateToMainActivity(this);
        this.finish();
    }

    @Override
    public void renderImportError() {
        Toast.makeText(this, "Error while importing lessons", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void renderFancyAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.root);
        rootLayout.clearAnimation();
        rootLayout.startAnimation(anim);
    }

}
