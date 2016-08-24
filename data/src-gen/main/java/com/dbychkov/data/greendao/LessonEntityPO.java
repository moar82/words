package com.dbychkov.data.greendao;

public class LessonEntityPO {
    private final Long id;
    private final String lessonName;
    private final boolean userLesson;
    private final String imagePath;
    private final boolean bookmarked;

    public LessonEntityPO(Long id, String lessonName, boolean userLesson, String imagePath, boolean bookmarked) {
        this.id = id;
        this.lessonName = lessonName;
        this.userLesson = userLesson;
        this.imagePath = imagePath;
        this.bookmarked = bookmarked;
    }

    public Long getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public boolean isUserLesson() {
        return userLesson;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }
}
