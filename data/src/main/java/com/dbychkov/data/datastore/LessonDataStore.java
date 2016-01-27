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

package com.dbychkov.data.datastore;

import com.dbychkov.data.greendao.LessonEntity;

import rx.Observable;

import java.util.List;

public interface LessonDataStore {

    Observable<List<LessonEntity>> getAllLessons();

    Observable<List<LessonEntity>> getBundledLessons();

    Observable<List<LessonEntity>> getUserLessons();

    Observable<List<LessonEntity>> getBookmarkedLessons();

    Observable<Boolean> bookmarkLesson(Long lessonEntityId);

    Observable<LessonEntity> addLesson(LessonEntity lessonEntity);

    Observable<LessonEntity> getLessonById(final Long lessonEntityId);

    Observable<Void> removeLessonById(final Long lessonEntityId);

    Observable<Void> bulkInsertLessons(final List<LessonEntity> lessonEntityList);

}
