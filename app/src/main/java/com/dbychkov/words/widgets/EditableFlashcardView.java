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

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dbychkov.words.R;

public class EditableFlashcardView extends CardView {

    private View inflatedView;
    private SwitchingEditText wordText;
    private SwitchingEditText wordDefinition;
    private Button removeButton;
    public ImageView speakerIconImageView;
    public ImageView learntIconImageView;

    public EditableFlashcardView(Context context) {
        super(context);
        initView();
    }

    public EditableFlashcardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EditableFlashcardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {


        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflatedView = inflater.inflate(R.layout.view_edit_words, this, true);


        wordText = (SwitchingEditText) inflatedView.findViewById(R.id.word_text);
        wordDefinition = (SwitchingEditText) inflatedView.findViewById(R.id.definition_text);
        removeButton = (Button) inflatedView.findViewById(R.id.remove_button);

        speakerIconImageView = (ImageView) inflatedView.findViewById(R.id.speaker_icon);
        learntIconImageView = (ImageView) inflatedView.findViewById(R.id.learnt_icon);
        learntIconImageView.setColorFilter(getContext().getResources().getColor(R.color.colorAccent));
        speakerIconImageView.setColorFilter(getContext().getResources().getColor(R.color.grey));
    }

    public void setWord(String word) {
        wordText.setElementText(word, "Flashcard");
    }

    public void setDefinition(String definition) {
        wordDefinition.setElementText(definition, "Definition");
    }

    public void setRemoveListener(OnClickListener listener) {
        removeButton.setOnClickListener(listener);
    }

    public void setOnWordSavedListener(SwitchingEditText.OnItemSavedListener onWordSavedListener) {
        ((SwitchingEditText)inflatedView.findViewById(R.id.word_text)).setListener(onWordSavedListener);
    }

    public void setOnDefinitionSavedListener(SwitchingEditText.OnItemSavedListener onDefinitionSavedListener) {
        wordDefinition.setListener(onDefinitionSavedListener);
    }

    public void setLearnt(boolean learnt){
        if (learnt) {
            learntIconImageView.setVisibility(View.VISIBLE);
        } else {
            learntIconImageView.setVisibility(View.GONE);
        }
    }

    public void setSpeakerIconClickListener(OnClickListener o){
        speakerIconImageView.setOnClickListener(o);
    }
}


