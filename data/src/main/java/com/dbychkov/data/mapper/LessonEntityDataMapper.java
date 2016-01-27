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

package com.dbychkov.data.mapper;

import com.dbychkov.data.greendao.LessonEntity;
import com.dbychkov.domain.Lesson;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link LessonEntity} in the data layer to {@link Lesson} in
 * the domain layer and vice versa
 */
@Singleton
public class LessonEntityDataMapper extends AbstractMapper<LessonEntity, Lesson> {

    @Inject
    public LessonEntityDataMapper() {
    }

    @Override
    public LessonEntity mapPojoToEntity(Lesson lesson) {
        LessonEntity lessonEntity = null;
        if (lesson != null) {
            lessonEntity = new LessonEntity();
            lessonEntity.setId(lesson.getId());
            lessonEntity.setBookmarked(lesson.isBookmarked());
            lessonEntity.setImagePath(lesson.getImagePath());
            lessonEntity.setLessonName(lesson.getLessonName());
            lessonEntity.setUserLesson(lesson.isUserLesson());
        }
        return lessonEntity;
    }

    @Override
    public Lesson mapEntityToPojo(LessonEntity lessonEntity) {
        Lesson lesson = null;
        if (lessonEntity != null) {
            lesson = new Lesson();
            lesson.setId(lessonEntity.getId());
            lesson.setBookmarked(lessonEntity.getBookmarked());
            lesson.setImagePath(lessonEntity.getImagePath());
            lesson.setLessonName(lessonEntity.getLessonName());
            lesson.setUserLesson(lessonEntity.getUserLesson());
        }
        return lesson;
    }
}
