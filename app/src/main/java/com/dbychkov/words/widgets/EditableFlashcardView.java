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

import butterknife.Bind;
import butterknife.ButterKnife;
import com.dbychkov.words.R;

/**
 * Dedicated view for a card with editable word and definition from flashcard
 */
public class EditableFlashcardView extends ReadOnlyFlashcardView {

    @Bind(R.id.remove_button)
    Button removeButton;

    @Bind(R.id.edit_button)
    Button editButton;

    @Bind(R.id.separator)
    View separator;

    public EditableFlashcardView(Context context) {
        super(context);
        setControlsVisible();
    }

    public EditableFlashcardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setControlsVisible();
    }

    public EditableFlashcardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setControlsVisible();
    }

    public void setRemoveListener(OnClickListener listener) {
        removeButton.setOnClickListener(listener);
    }

    public void setEditListener(OnClickListener listener) {
        editButton.setOnClickListener(listener);
    }

    private void setControlsVisible(){
        removeButton.setVisibility(VISIBLE);
        editButton.setVisibility(VISIBLE);
        separator.setVisibility(VISIBLE);
    }
}


