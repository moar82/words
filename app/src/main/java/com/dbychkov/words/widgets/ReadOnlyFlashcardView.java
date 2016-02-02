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

package com.dbychkov.words.widgets;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dbychkov.words.R;

/**
 * Dedicated view for a card with word and definition from flashcard
 */
public class ReadOnlyFlashcardView extends CardView {

    @Bind(R.id.word)
    public TextView wordTextView;

    @Bind(R.id.definition)
    public TextView definitionTextView;

    @Bind(R.id.speaker_icon)
    public ImageView speakerIconImageView;

    @Bind(R.id.learnt_icon)
    public ImageView learntIconImageView;

    public ReadOnlyFlashcardView(Context context) {
        super(context);
        initView();
    }

    public ReadOnlyFlashcardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ReadOnlyFlashcardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.view_flashcards, this, true);
        ButterKnife.bind(this, inflatedView);
    }

    public void setFlashcardWord(String word) {
        wordTextView.setText(word);
    }

    public void setFlashcardDefinition(String definition) {
        definitionTextView.setText(definition);
    }

    public void setLearnt(boolean learnt) {
        if (learnt) {
            learntIconImageView.setVisibility(View.VISIBLE);
        } else {
            learntIconImageView.setVisibility(View.GONE);
        }
    }

    public void setSpeakerIconClickListener(OnClickListener o) {
        speakerIconImageView.setOnClickListener(o);
    }

    public String getWord() {
        return wordTextView.getText().toString();
    }
}
