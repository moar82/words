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

package com.dbychkov.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbychkov.domain.Flashcard;
import com.dbychkov.words.R;
import com.dbychkov.words.presentation.EditFlashcardsActivityPresenter;
import com.dbychkov.words.widgets.EditableFlashcardView;

import javax.inject.Inject;

/**
 * Adapter for displaying list of editable flashcards
 */
public class EditFlashcardsAdapter extends BaseListAdapter<Flashcard, EditableFlashcardView> {

    private EditFlashcardsActivityPresenter presenter;

    @Inject
    public EditFlashcardsAdapter(Context context, EditFlashcardsActivityPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected EditableFlashcardView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (EditableFlashcardView) LayoutInflater.from(context)
                .inflate(R.layout.adapter_item_edit_words, viewGroup, false);
    }

    @Override
    protected void bind(final Flashcard flashcard, EditableFlashcardView view, final ViewHolder<EditableFlashcardView> holder) {
        if (flashcard != null) {
            view.setFlashcardWord(flashcard.getWord());
            view.setFlashcardDefinition(flashcard.getDefinition());


//
//            view.setOnWordSavedListener(new SwitchingEditText.OnItemSavedListener() {
//                @Override
//                public void onItemSaved(String wordFromView) {
//                    flashcard.setWord(wordFromView);
//                    presenter.flashcardModified(flashcard);
//                }
//            });
//            view.setOnDefinitionSavedListener(new SwitchingEditText.OnItemSavedListener() {
//                @Override
//                public void onItemSaved(String definitionFromView) {
//                    flashcard.setDefinition(definitionFromView);
//                    presenter.flashcardModified(flashcard);
//                }
//            });
            view.setLearnt(flashcard.isLearnt());
            view.setSpeakerIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSpeakIconClicked(flashcard.getWord());
                }
            });
            view.setRemoveListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    presenter.onFlashcardRemoveClicked(flashcard, holder.getAdapterPosition());
                }
            });
            view.setEditListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    presenter.onFlashcardEditClicked(flashcard, holder.getAdapterPosition());

                }
            });
        }
    }


}
