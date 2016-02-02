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

import com.dbychkov.data.datastore.FlashcardDataStore;
import com.dbychkov.data.datastore.FlashcardDataStoreFactory;
import com.dbychkov.data.greendao.FlashcardEntity;
import com.dbychkov.data.mapper.FlashcardEntityDataMapper;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.repository.FlashcardRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link com.dbychkov.domain.repository.FlashcardRepository} for managing flashcards data
 */
@Singleton
public class FlashcardDataRepository implements FlashcardRepository {

    private final FlashcardDataStoreFactory wordDataStoreFactory;
    private final FlashcardEntityDataMapper flashcardEntityDataMapper;

    @Inject
    public FlashcardDataRepository(FlashcardDataStoreFactory wordDataStoreFactory,
                                   FlashcardEntityDataMapper flashcardEntityDataMapper) {
        this.wordDataStoreFactory = wordDataStoreFactory;
        this.flashcardEntityDataMapper = flashcardEntityDataMapper;
    }

    private FlashcardDataStore dataStore() {
        return wordDataStoreFactory.createDatabaseDataStore();
    }

    @Override
    public Observable<Flashcard> addFlashcard(Flashcard flashcard) {
        return dataStore().addFlashcard(flashcardEntityDataMapper.mapPojoToEntity(flashcard)).map(new Func1<FlashcardEntity, Flashcard>() {
            @Override
            public Flashcard call(FlashcardEntity flashcardEntity) {
                return flashcardEntityDataMapper.mapEntityToPojo(flashcardEntity);
            }
        });
    }

    @Override
    public Observable<Void> removeFlashcard(Long flashcardId) {
        return dataStore().removeFlashcard(flashcardId);
    }

    @Override
    public Observable<Void> bulkInsertFlashcards(List<Flashcard> flashcardList) {
        return dataStore().bulkInsertFlashcards(flashcardEntityDataMapper.mapPojoListToEntityList(flashcardList));
    }

    @Override
    public Observable<Flashcard> getFlashcardById(Long flashcardId) {
        return dataStore().getFlashcardById(flashcardId).map(new Func1<FlashcardEntity, Flashcard>() {
            @Override
            public Flashcard call(FlashcardEntity flashcardEntity) {
                return flashcardEntityDataMapper.mapEntityToPojo(flashcardEntity);
            }
        });
    }

    @Override
    public Observable<Void> updateFlashcard(Flashcard flashcard) {
        return dataStore().updateFlashcard(flashcardEntityDataMapper.mapPojoToEntity(flashcard));
    }

    @Override
    public Observable<List<Flashcard>> getAllFlashcards() {
        return dataStore().getAllFlashcards().map(new Func1<List<FlashcardEntity>, List<Flashcard>>() {
            @Override
            public List<Flashcard> call(List<FlashcardEntity> flashcardEntityList) {
                return flashcardEntityDataMapper.mapEntityListToPojoList(flashcardEntityList);
            }
        });
    }

    @Override
    public Observable<List<Flashcard>> getFlashcardsFromLesson(Long lessonId) {
        return dataStore().getFlashcardsFromLesson(lessonId).map(new Func1<List<FlashcardEntity>, List<Flashcard>>() {
            @Override
            public List<Flashcard> call(List<FlashcardEntity> flashcardEntityList) {
                return flashcardEntityDataMapper.mapEntityListToPojoList(flashcardEntityList);
            }
        });
    }

    @Override
    public Observable<List<Flashcard>> getUnlearntFlashcardsFromLesson(Long lessonId) {
        return dataStore().getUnlearntFlashcardsFromLesson(lessonId).map(new Func1<List<FlashcardEntity>, List<Flashcard>>() {
            @Override
            public List<Flashcard> call(List<FlashcardEntity> flashcardEntityList) {
                return flashcardEntityDataMapper.mapEntityListToPojoList(flashcardEntityList);
            }
        });
    }

    @Override
    public Observable<Void> clearProgressForLesson(Long lessonId) {
        return dataStore().clearProgressForLesson(lessonId);
    }
}
