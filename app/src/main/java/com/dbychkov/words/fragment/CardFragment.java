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

package com.dbychkov.words.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.words.R;
import com.dbychkov.words.dagger.component.ActivityComponent;

public class CardFragment extends BaseFragment {

    private static final String BUNDLE_STRING_WORD = "englishWord";
    private static final String BUNDLE_STRING_DEFINITION = "definition";
    private static final String BUNDLE_LONG_CARD_ID = "cardId";
    private static final String BUNDLE_BOOLEAN_CARD_FRONT = "cardFront";

    private View rootView;
    private String englishWord;
    private String definition;
    private boolean front = false;

    public CardFragment() {
    }

    public static CardFragment newCardFrontInstance(Flashcard flashcard) {
        return newInstance(flashcard, true);
    }

    public static CardFragment newCardBackInstance(Flashcard flashcard) {
        return newInstance(flashcard, false);
    }

    private static CardFragment newInstance(Flashcard flashcard, boolean cardFront) {
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_STRING_WORD, flashcard.getWord());
        bundle.putLong(BUNDLE_LONG_CARD_ID, flashcard.getId());
        bundle.putString(BUNDLE_STRING_DEFINITION, flashcard.getDefinition());
        bundle.putBoolean(BUNDLE_BOOLEAN_CARD_FRONT, cardFront);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        englishWord = getArguments().getString(BUNDLE_STRING_WORD);
        definition = getArguments().getString(BUNDLE_STRING_DEFINITION);
        front = getArguments().getBoolean(BUNDLE_BOOLEAN_CARD_FRONT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card, container, false);
        initWord();
        return rootView;
    }

    private void initWord() {
        if (front) {
            ((TextView) rootView.findViewById(R.id.front_text)).setText(englishWord);
        } else {
            ((TextView) rootView.findViewById(R.id.front_text)).setText(definition);
        }
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }
}
