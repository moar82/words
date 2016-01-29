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

package com.dbychkov.words.presentation;

import com.dbychkov.domain.Lesson;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;

import java.util.Random;

/**
 * Presenter for custom lessons created by users
 */
public class UserLessonsTabFragmentPresenter extends LessonsPresenter {

    private LessonRepository lessonRepository;

    public UserLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        super(threadExecutor, postExecutionThread, lessonRepository.getUserLessons(), rxEventBus);
        this.lessonRepository = lessonRepository;
    }

    public void createNewLessonButtonClicked() {
        final Lesson lesson = new Lesson();
        lesson.setLessonName("User lesson");
        lesson.setUserLesson(true);
        lesson.setImagePath(getRandomImage());
        execute(lessonRepository.addLesson(lesson), new DefaultSubscriber<Lesson>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Lesson lesson) {
                renderLessonsView.renderCreatedLesson(lesson);
            }
        });
    }

    public static String getRandomImage() {
        int unit = new Random().nextInt(20) + 1;
        return "random/random_" + unit + ".png";
    }

    public void lessonItemClicked(Long lessonId, final int position) {
        execute(lessonRepository.removeLessonById(lessonId), new DefaultSubscriber<Void>() {

            @Override
            public void onNext(Void v) {
                renderLessonsView.renderLessonItemRemoved(position);
            }
        });

    }
}
