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
import com.dbychkov.data.greendao.LessonEntity;
import com.dbychkov.data.greendao.LessonEntityDao;

import rx.Observable;
import rx.Subscriber;

import java.util.List;

public class DatabaseLessonDataStore implements LessonDataStore {

    private final LessonEntityDao lessonEntityDao;

    public DatabaseLessonDataStore(DaoSession daoSession) {
        this.lessonEntityDao = daoSession.getLessonEntityDao();
    }

    @Override
    public Observable<List<LessonEntity>> getAllLessons() {
        return Observable.create(new Observable.OnSubscribe<List<LessonEntity>>() {
            @Override
            public void call(Subscriber<? super List<LessonEntity>> subscriber) {
                subscriber.onNext(
                        lessonEntityDao
                                .queryBuilder()
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<LessonEntity>> getBundledLessons() {
        return Observable.create(new Observable.OnSubscribe<List<LessonEntity>>() {
            @Override
            public void call(Subscriber<? super List<LessonEntity>> subscriber) {
                subscriber.onNext(
                        lessonEntityDao
                                .queryBuilder()
                                .where(LessonEntityDao.Properties.UserLesson.eq(false))
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<LessonEntity>> getUserLessons() {
        return Observable.create(new Observable.OnSubscribe<List<LessonEntity>>() {
            @Override
            public void call(Subscriber<? super List<LessonEntity>> subscriber) {
                subscriber.onNext(
                        lessonEntityDao
                                .queryBuilder()
                                .where(LessonEntityDao.Properties.UserLesson.eq(true))
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<LessonEntity>> getBookmarkedLessons() {
        return Observable.create(new Observable.OnSubscribe<List<LessonEntity>>() {
            @Override
            public void call(Subscriber<? super List<LessonEntity>> subscriber) {
                subscriber.onNext(
                        lessonEntityDao
                                .queryBuilder()
                                .where(LessonEntityDao.Properties.Bookmarked.eq(true))
                                .list()
                );
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> bookmarkLesson(final Long lessonEntityId) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                LessonEntity lesson = lessonEntityDao.loadByRowId(lessonEntityId);
                lesson.setBookmarked(!lesson.getBookmarked());
                lessonEntityDao.update(lesson);
                subscriber.onNext(lesson.getBookmarked());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<LessonEntity> addLesson(final LessonEntity lessonEntity) {
        return Observable.create(new Observable.OnSubscribe<LessonEntity>() {
            @Override
            public void call(Subscriber<? super LessonEntity> subscriber) {
                lessonEntityDao.insert(lessonEntity);
                subscriber.onNext(lessonEntity);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<LessonEntity> getLessonById(final Long lessonEntityId) {
        return Observable.create(new Observable.OnSubscribe<LessonEntity>() {
            @Override
            public void call(Subscriber<? super LessonEntity> subscriber) {
                subscriber.onNext(lessonEntityDao.loadByRowId(lessonEntityId));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> removeLessonById(final Long lessonEntityId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                lessonEntityDao.loadByRowId(lessonEntityId).delete();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> bulkInsertLessons(final List<LessonEntity> lessonEntityList) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                lessonEntityDao.insertInTx(lessonEntityList);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

}
