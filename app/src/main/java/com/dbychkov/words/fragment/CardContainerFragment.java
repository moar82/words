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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.words.R;
import com.dbychkov.words.dagger.component.ActivityComponent;

/**
 * Flashcard container
 */
public class CardContainerFragment extends BaseFragment implements View.OnClickListener {

    private static final String ENTRY_ARGUMENT = "entry";

    private boolean cardFlipped = false;
    private Flashcard entry;

    public static CardContainerFragment newInstance(Flashcard flashcard) {
        CardContainerFragment fragment = new CardContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ENTRY_ARGUMENT, flashcard);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CardContainerFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entry = (Flashcard) getArguments().getSerializable(ENTRY_ARGUMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_container, container, false);
        rootView.setOnClickListener(this);
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container, CardFragment.newCardFrontInstance(entry))
                .commit();
        return rootView;
    }

    public boolean isFlipped() {
        return cardFlipped;
    }

    public void flipCard() {
        Fragment newFragment;
        if (cardFlipped) {
            newFragment = CardFragment.newCardFrontInstance(entry);
        } else {
            newFragment = CardFragment.newCardBackInstance(entry);
        }

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, newFragment)
                .commit();

        cardFlipped = !cardFlipped;
    }

    @Override
    public void onClick(View v) {
        flipCard();
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }
}