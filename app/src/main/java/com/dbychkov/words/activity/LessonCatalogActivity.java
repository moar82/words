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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dbychkov.words.R;
import com.dbychkov.words.adapter.LessonsPageAdapter;
import com.dbychkov.words.dagger.component.ActivityComponent;
import com.dbychkov.words.presentation.LessonCatalogActivityPresenter;
import com.dbychkov.words.util.LogHelper;
import com.dbychkov.words.util.MarketService;
import com.dbychkov.words.util.ShareService;
import com.dbychkov.words.view.LessonCatalogView;
import com.mikepenz.aboutlibraries.LibsBuilder;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Main activity with list of lessons (installed lessons, bookmarked lessons, user lessons).
 */
public class LessonCatalogActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        LessonCatalogView {

    private static final String TAG = LogHelper.makeLogTag(LessonCatalogActivity.class);

    public static final int OFF_SCREEN_PAGE_LIMIT = 2;

    @Inject
    LessonCatalogActivityPresenter lessonCatalogActivityPresenter;

    @Inject
    MarketService marketService;

    @Inject
    ShareService shareService;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @BindString(R.string.title_activity_lesson_catalog)
    String title;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_catalog);
        ButterKnife.bind(this);
        initToolbar();
        initTabs();
        initTitle();
        lessonCatalogActivityPresenter.setView(this);
        lessonCatalogActivityPresenter.initialize();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }


    private void initTabs() {
        viewPager.setAdapter(new LessonsPageAdapter(getFragmentManager()));
        viewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
    }

    private void initTitle() {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                lessonCatalogActivityPresenter.shareButtonClicked();
                return true;
            case R.id.rate:
                lessonCatalogActivityPresenter.rateButtonClicked();
                return true;
            case R.id.about:
                lessonCatalogActivityPresenter.aboutButtonClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void renderRateScreen() {
        marketService.showAppPageInGooglePlay(LessonCatalogActivity.this.getPackageName());
    }

    @Override
    public void renderShareScreen() {
        shareService.shareAsPlainText(LessonCatalogActivity.this);
    }

    @Override
    public void renderAboutScreen() {
        new LibsBuilder()
                .withAutoDetect(true)
                .withLicenseShown(true)
                .withVersionShown(true)
                .withActivityTitle(getString(R.string.menu_about))
                .withAboutAppName(getString(R.string.app_name))
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withActivityTheme(R.style.Base_Theme_Design_Dark)
                .start(LessonCatalogActivity.this);
    }
}
