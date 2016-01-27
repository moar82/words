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

import com.dbychkov.domain.Lesson;

import java.util.List;

import rx.Observable;

/**
 * Repository for getting {@link Lesson} related data
 */
public interface LessonRepository {

    /**
     * Get an {@link Observable} which will emit List of all {@link Lesson}
     */
    Observable<List<Lesson>> getAllLessons();

    /**
     * Get an {@link Observable} which will emit List of bundled {@link Lesson}
     */
    Observable<List<Lesson>> getBundledLessons();

    /**
     * Get an {@link Observable} which will emit List of {@link Lesson} created by user
     */
    Observable<List<Lesson>> getUserLessons();

    /**
     * Get an {@link Observable} which will emit List of {@link Lesson} bookmarked by user
     */
    Observable<List<Lesson>> getBookmarkedLessons();

    /**
     * Get an {@link Observable} which will notify the modification of bookmarked state of {@link Lesson} object
     */
    Observable<Boolean> bookmarkLesson(Long lessonId);

    /**
     * Get an {@link Observable} which will notify the addition of a {@link Lesson} object
     */
    Observable<Lesson> addLesson(Lesson lesson);

    /**
     * Get an {@link Observable} which will emit a {@link Lesson} by lesson id
     */
    Observable<Lesson> getLessonById(final Long lessonId);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link Lesson} object by lesson id
     */
    Observable<Void> removeLessonById(final Long lessonId);

    /**
     * Get an {@link Observable} which will notify the bulk insertion of {@link Lesson} objects
     */
    Observable<Void> bulkInsertLessons(final List<Lesson> lessonList);

}
