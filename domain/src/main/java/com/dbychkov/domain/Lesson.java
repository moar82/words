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

package com.dbychkov.domain;

import java.util.List;

/**
 * Represents a lesson consisting of a number of flashcards
 */
public class Lesson {

    private Long id;
    private String lessonName;
    private boolean userLesson;
    private String imagePath;
    private boolean bookmarked;
    private List<Flashcard> flashcardList;

    public Lesson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public boolean isUserLesson() {
        return userLesson;
    }

    public void setUserLesson(boolean userLesson) {
        this.userLesson = userLesson;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public List<Flashcard> getFlashcardList() {
        return flashcardList;
    }

    public void setFlashcardList(List<Flashcard> flashcardList) {
        this.flashcardList = flashcardList;
    }
}
