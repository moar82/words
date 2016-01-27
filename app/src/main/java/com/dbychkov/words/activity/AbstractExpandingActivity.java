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

package com.dbychkov.words.activity;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.dbychkov.words.R;
import com.dbychkov.words.util.LogHelper;

/**
 * Base activity for reveal transitions
 */
public abstract class AbstractExpandingActivity extends BaseActivity {

    private static final String TAG = LogHelper.makeLogTag(AbstractExpandingActivity.class);

    private static final int ANIM_DURATION = 300;

    public static final String EXTRA_PROPERTY_TOP = "top";
    public static final String EXTRA_PROPERTY_LEFT = "left";
    public static final String EXTRA_PROPERTY_WIDTH = "width";
    public static final String EXTRA_PROPERTY_HEIGHT = "height";

    public static final String BUNDLE_DELTA_LEFT = "deltaLeft";
    public static final String BUNDLE_DELTA_TOP = "deltaTop";
    public static final String BUNDLE_WIDTH_SCALE = "deltaWidth";
    public static final String BUNDLE_HEIGHT_SCALE = "deltaHeight";

    protected Toolbar toolbar;
    private View rootLayout;

    private int originalViewTop;
    private int originalViewLeft;
    private int originalViewWidth;
    private int originalViewHeight;

    private int deltaLeft;
    private int deltaTop;
    private float widthScale;
    private float heightScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateExpandingActivity(savedInstanceState);
        initRootViewBackground();
        initToolbar();
        initExtra();
        initAnimation(savedInstanceState);
    }

    private void initRootViewBackground() {
        rootLayout = getRootLayout();
        rootLayout.setBackground(new ColorDrawable(getResources().getColor(R.color.grey)));
    }

    private void initAnimation(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            ViewTreeObserver observer = rootLayout.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rootLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    initDeltas();
                    runEnterAnimation();
                    return true;
                }
            });
        }
    }

    private void initDeltas() {
        deltaLeft = originalViewLeft;
        deltaTop = originalViewTop - getStatusBarHeight();
        widthScale = (float) originalViewWidth / getWidthForScale();
        heightScale = (float) originalViewHeight / getHeightForScale();
    }

    public int getHeightForScale() {
        return getDisplayMetrics().heightPixels;
    }

    public int getWidthForScale() {
        return getDisplayMetrics().widthPixels;
    }

    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics;
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
        }
    }

    private int getStatusBarHeight() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarTop = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return Math.abs(statusBarTop - contentViewTop);
    }

    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        rootLayout.setPivotX(0);
        rootLayout.setPivotY(0);
        rootLayout.setScaleX(widthScale);
        rootLayout.setScaleY(heightScale);
        rootLayout.setTranslationX(deltaLeft);
        rootLayout.setTranslationY(deltaTop);
        rootLayout.setAlpha(0.3f);
        rootLayout
                .animate()
                .setDuration(duration)
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                .alpha(1);
    }

    private void runExitAnimation(final Runnable endAction) {
        rootLayout.setPivotX(0);
        rootLayout.setPivotY(0);
        rootLayout
                .animate()
                .setDuration(ANIM_DURATION)
                .scaleX(widthScale)
                .scaleY(heightScale)
                .translationX(deltaLeft)
                .translationY(deltaTop)
                .alpha(0.3f)
                .withEndAction(endAction);
    }

    @Override
    public void onBackPressed() {
        runExitAnimation(new Runnable() {
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                runExitAnimation(new Runnable() {
                    public void run() {
                        finish();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initExtra() {
        originalViewTop = getIntent().getIntExtra(EXTRA_PROPERTY_TOP, 0);
        originalViewLeft = getIntent().getIntExtra(EXTRA_PROPERTY_LEFT, 0);
        originalViewWidth = getIntent().getIntExtra(EXTRA_PROPERTY_WIDTH, 0);
        originalViewHeight = getIntent().getIntExtra(EXTRA_PROPERTY_HEIGHT, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_DELTA_LEFT, deltaLeft);
        outState.putInt(BUNDLE_DELTA_TOP, deltaTop);
        outState.putFloat(BUNDLE_WIDTH_SCALE, widthScale);
        outState.putFloat(BUNDLE_HEIGHT_SCALE, heightScale);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        deltaLeft = savedInstanceState.getInt(BUNDLE_DELTA_LEFT);
        deltaTop = savedInstanceState.getInt(BUNDLE_DELTA_TOP);
        widthScale = savedInstanceState.getFloat(BUNDLE_WIDTH_SCALE);
        heightScale = savedInstanceState.getFloat(BUNDLE_HEIGHT_SCALE);
    }

    public abstract View getRootLayout();

    public abstract void onCreateExpandingActivity(Bundle savedInstanceState);

}
