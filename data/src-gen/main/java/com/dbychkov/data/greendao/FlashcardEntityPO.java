package com.dbychkov.data.greendao;

public class FlashcardEntityPO {
    private final Long id;
    private final String word;
    private final String definition;
    private final int status;
    private final Long lessonId;

    public FlashcardEntityPO(Long id, String word, String definition, int status, Long lessonId) {
        this.id = id;
        this.word = word;
        this.definition = definition;
        this.status = status;
        this.lessonId = lessonId;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public int getStatus() {
        return status;
    }

    public Long getLessonId() {
        return lessonId;
    }
}
