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

package com.dbychkov.data.repository;

import com.dbychkov.data.datastore.LessonDataStore;
import com.dbychkov.data.datastore.LessonDataStoreFactory;
import com.dbychkov.data.greendao.LessonEntity;
import com.dbychkov.data.mapper.LessonEntityDataMapper;
import com.dbychkov.domain.Lesson;
import com.dbychkov.domain.repository.LessonRepository;
import rx.Observable;
import rx.functions.Func1;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * {@link com.dbychkov.domain.repository.LessonRepository} for managing lessons data
 */
@Singleton
public class LessonDataRepository implements LessonRepository {

    private final LessonDataStoreFactory lessonDataStoreFactory;
    private final LessonEntityDataMapper lessonEntityDataMapper;

    @Inject
    public LessonDataRepository(LessonDataStoreFactory lessonDataStoreFactory,
            LessonEntityDataMapper lessonEntityDataMapper) {
        this.lessonDataStoreFactory = lessonDataStoreFactory;
        this.lessonEntityDataMapper = lessonEntityDataMapper;
    }

    private LessonDataStore dataStore(){
        return lessonDataStoreFactory.createDatabaseDataStore();
    }

    @Override
    public Observable<List<Lesson>> getAllLessons() {
        return dataStore().getAllLessons().map(new Func1<List<LessonEntity>, List<Lesson>>() {
            @Override
            public List<Lesson> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<List<Lesson>> getBundledLessons() {
        return dataStore().getBundledLessons().map(new Func1<List<LessonEntity>, List<Lesson>>() {
            @Override
            public List<Lesson> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<List<Lesson>> getUserLessons() {
        return dataStore().getUserLessons().map(new Func1<List<LessonEntity>, List<Lesson>>() {
            @Override
            public List<Lesson> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<List<Lesson>> getBookmarkedLessons() {
        return dataStore().getBookmarkedLessons().map(new Func1<List<LessonEntity>, List<Lesson>>() {
            @Override
            public List<Lesson> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<Boolean> bookmarkLesson(Long lessonEntityId) {
        return dataStore().bookmarkLesson(lessonEntityId);
    }

    @Override
    public Observable<Lesson> addLesson(Lesson lesson) {
        return dataStore().addLesson(lessonEntityDataMapper.mapPojoToEntity(lesson)).map(new Func1<LessonEntity, Lesson>() {
            @Override
            public Lesson call(LessonEntity lessonEntity) {
                return lessonEntityDataMapper.mapEntityToPojo(lessonEntity);
            }
        });
    }

    @Override
    public Observable<Lesson> getLessonById(Long lessonEntityId) {
        return dataStore().getLessonById(lessonEntityId).map(new Func1<LessonEntity, Lesson>() {
            @Override
            public Lesson call(LessonEntity lessonEntity) {
                return lessonEntityDataMapper.mapEntityToPojo(lessonEntity);
            }
        });
    }

    @Override
    public Observable<Void> removeLessonById(Long lessonEntityId) {
        return dataStore().removeLessonById(lessonEntityId);
    }

    @Override
    public Observable<Void> bulkInsertLessons(List<Lesson> lessonEntityList) {
        return dataStore().bulkInsertLessons(lessonEntityDataMapper.mapPojoListToEntityList(lessonEntityList));
    }
}
