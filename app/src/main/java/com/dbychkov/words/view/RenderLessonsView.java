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

import com.dbychkov.domain.Lesson;

import java.util.List;

/**
 * View, capable of rendering list of lessons
 */
public interface RenderLessonsView extends LoadView {

    void renderLessonList(List<Lesson> lessonList);

    void renderCreatedLesson(Lesson lesson);

    void renderLessonItemRemoved(int position);

    void renderLessonItemBookmarked(int position, boolean bookmarked);
}
