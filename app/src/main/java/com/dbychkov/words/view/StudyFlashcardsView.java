/*
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

package com.dbychkov.words.view;

import com.dbychkov.domain.Flashcard;
import com.dbychkov.words.presentation.StudyFlashcardsActivityPresenter;

import java.util.List;

/**
 * View, capable of rendering flashcards
 */
public interface StudyFlashcardsView extends View {

    void renderFlashcards(List<Flashcard> flashcards);

    void showFlashcard(int flashcardNumber);

    boolean showCardBack(int flashcardNumber);

    void showLessonEndedDialog();

    void showAllWordsLearntDialog();

    void setView(StudyFlashcardsActivityPresenter studyFlashcardsActivityPresenter);
}
