/**
 * Copyright (C) dbychkov.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dbychkov.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbychkov.domain.Flashcard;
import com.dbychkov.words.R;
import com.dbychkov.words.presentation.ViewFlashcardsActivityPresenter;
import com.dbychkov.words.widgets.ReadOnlyFlashcardView;

import javax.inject.Inject;

/**
 * Adapter for rendering list of flashcards
 */
public class ViewFlashcardsAdapter extends BaseListAdapter<Flashcard, ReadOnlyFlashcardView> {

    private ViewFlashcardsActivityPresenter presenter;

    @Inject
    public ViewFlashcardsAdapter(Context context, ViewFlashcardsActivityPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected ReadOnlyFlashcardView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (ReadOnlyFlashcardView) LayoutInflater.from(context)
                .inflate(R.layout.adapter_item_learn_words, viewGroup, false);
    }

    @Override
    protected void bind(final Flashcard flashcard, ReadOnlyFlashcardView view, ViewHolder<ReadOnlyFlashcardView> viewHolder) {
        view.setFlashcardWord(flashcard.getWord());
        view.setFlashcardDefinition(flashcard.getDefinition());
        view.setLearnt(flashcard.isLearnt());
        view.setSpeakerIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSpeakIconClicked(flashcard.getWord());
            }
        });
    }
}