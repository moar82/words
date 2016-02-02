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

package com.dbychkov.words.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * RecyclerView which shows placeholder text when no elements are present in the list
 */
public class RecyclerViewWithEmptyView extends RecyclerView {

    private static final long ON_REMOVED_ANIMATION_DURATION = 300;
    private View emptyView;

    public RecyclerViewWithEmptyView(Context context) {
        super(context);
    }

    public RecyclerViewWithEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty(boolean removed) {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            if (emptyViewVisible) {
                emptyView.setVisibility(VISIBLE);
                setVisibility(GONE);
               if (removed) {
                   ObjectAnimator
                           .ofFloat(emptyView, "alpha", new float[]{0, 1.0F})
                           .setDuration(ON_REMOVED_ANIMATION_DURATION)
                           .start();
               }
            } else {
                emptyView.setVisibility(GONE);
                setVisibility(VISIBLE);
            }
        }
    }

    private final AdapterDataObserver listEmptyListener = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            checkIfEmpty(false);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty(false);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty(true);
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(listEmptyListener);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(listEmptyListener);
        }
        checkIfEmpty(false);
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty(false);
    }

}
