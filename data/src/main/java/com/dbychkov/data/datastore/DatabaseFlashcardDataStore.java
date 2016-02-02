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

import com.dbychkov.data.greendao.DaoSession;
import com.dbychkov.data.greendao.FlashcardEntity;
import com.dbychkov.data.greendao.FlashcardEntityDao;

import rx.Observable;
import rx.Subscriber;

import java.util.List;

public class DatabaseFlashcardDataStore implements FlashcardDataStore {

    private final FlashcardEntityDao flashcardEntityDao;

    public DatabaseFlashcardDataStore(DaoSession daoSession) {
        this.flashcardEntityDao = daoSession.getFlashcardEntityDao();
    }

    @Override
    public Observable<FlashcardEntity> addFlashcard(final FlashcardEntity flashcardEntity) {
        return Observable.create(new Observable.OnSubscribe<FlashcardEntity>() {
            @Override
            public void call(Subscriber<? super FlashcardEntity> subscriber) {
                flashcardEntityDao.insert(flashcardEntity);
                subscriber.onNext(flashcardEntity);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> removeFlashcard(final Long wordEntityId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                flashcardEntityDao.loadByRowId(wordEntityId).delete();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> bulkInsertFlashcards(final List<FlashcardEntity> wordEntityList) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                flashcardEntityDao.insertInTx(wordEntityList);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<FlashcardEntity> getFlashcardById(final Long wordId) {
        return Observable.create(new Observable.OnSubscribe<FlashcardEntity>() {
            @Override
            public void call(Subscriber<? super FlashcardEntity> subscriber) {
                subscriber.onNext(flashcardEntityDao.loadByRowId(wordId));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> updateFlashcard(final FlashcardEntity wordEntity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                flashcardEntityDao.update(wordEntity);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<FlashcardEntity>> getAllFlashcards() {
        return Observable.create(new Observable.OnSubscribe<List<FlashcardEntity>>() {
            @Override
            public void call(Subscriber<? super List<FlashcardEntity>> subscriber) {
                subscriber.onNext(
                        flashcardEntityDao
                                .queryBuilder()
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<FlashcardEntity>> getFlashcardsFromLesson(final Long lessonEntityId) {
        return Observable.create(new Observable.OnSubscribe<List<FlashcardEntity>>() {
            @Override
            public void call(Subscriber<? super List<FlashcardEntity>> subscriber) {
                subscriber.onNext(
                        flashcardEntityDao
                                .queryBuilder()
                                .where(FlashcardEntityDao.Properties.LessonId.eq(lessonEntityId))
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> clearProgressForLesson(final Long lessonEntityId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                List<FlashcardEntity> wordsFromLesson = flashcardEntityDao
                        .queryBuilder()
                        .where(FlashcardEntityDao.Properties.LessonId.eq(lessonEntityId))
                        .list();
                for (FlashcardEntity wordEntity : wordsFromLesson) {
                    wordEntity.setStatus(0);
                    updateFlashcard(wordEntity);
                    flashcardEntityDao.update(wordEntity);
                }
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<FlashcardEntity>> getUnlearntFlashcardsFromLesson(final Long lessonEntityId) {
        return Observable.create(new Observable.OnSubscribe<List<FlashcardEntity>>() {
            @Override
            public void call(Subscriber<? super List<FlashcardEntity>> subscriber) {
                subscriber.onNext(
                        flashcardEntityDao
                                .queryBuilder()
                                .where(FlashcardEntityDao.Properties.LessonId.eq(lessonEntityId))
                                .where(FlashcardEntityDao.Properties.Status.eq(0))
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }
}
