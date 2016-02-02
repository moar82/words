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

package com.dbychkov.domain.repository;

import com.dbychkov.domain.Flashcard;

import rx.Observable;

import java.util.List;

/**
 * Repository for getting {@link Flashcard} related data
 */
public interface FlashcardRepository {

    /**
     * Get an {@link Observable} which will notify the addition of a {@link Flashcard} object
     */
    Observable<Flashcard> addFlashcard(Flashcard flashcard);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link Flashcard} object
     */
    Observable<Void> removeFlashcard(Long flashcardId);

    /**
     * Get an {@link Observable} which will notify the bulk insertion of {@link Flashcard} objects
     */
    Observable<Void> bulkInsertFlashcards(List<Flashcard> flashcardList);

    /**
     * Get an {@link Observable} which will emit a {@link Flashcard} object by id
     */
    Observable<Flashcard> getFlashcardById(Long flashcardId);

    /**
     * Get an {@link Observable} which will notify the edition of {@link Flashcard} object
     */
    Observable<Void> updateFlashcard(Flashcard flashcard);

    /**
     * Get an {@link Observable} which will emit a List of all {@link Flashcard} objects
     */
    Observable<List<Flashcard>> getAllFlashcards();

    /**
     * Get an {@link Observable} which will emit a List of all {@link Flashcard} objects for lesson with particular id
     */
    Observable<List<Flashcard>> getFlashcardsFromLesson(Long lessonId);

    /**
     * Get an {@link Observable} which will emit a List of unlearnt {@link Flashcard} objects for lesson with particular id
     */
    Observable<List<Flashcard>> getUnlearntFlashcardsFromLesson(Long lessonId);

    /**
     * Get an {@link Observable} which will notify the removal of progress for all {@link Flashcard} objects of particular lesson
     */
    Observable<Void> clearProgressForLesson(Long lessonId);
}
