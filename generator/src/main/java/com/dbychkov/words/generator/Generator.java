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

package com.dbychkov.words.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for "Words" application
 */
public class Generator {

    private static final String OUTPUT_DIR = "../data/src-gen/main/java/";
    private static final String PACKAGE = "com.dbychkov.data.greendao";
    private static final int SCHEMA_VERSION = 20;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(SCHEMA_VERSION, PACKAGE);

        Entity packsTable = addLessonsTable(schema);
        Entity wordsTable = addFlashcardsTable(schema);
        bidirectionalOneToManyRelation(packsTable, wordsTable);

        new DaoGenerator().generateAll(schema, OUTPUT_DIR);
    }

    /**
     * Add "LESSONS" table to schema
     * @Since 1
     */
    private static Entity addLessonsTable(Schema schema) {
        Entity lesson = schema.addEntity("LessonEntity");
        lesson.setTableName("LESSONS");
        lesson.addIdProperty().autoincrement();
        lesson.addStringProperty("lessonName").notNull();
        lesson.addBooleanProperty("userLesson").notNull();
        lesson.addStringProperty("imagePath").notNull();
        lesson.addBooleanProperty("bookmarked").notNull();
        lesson.implementsSerializable();
        return lesson;
    }

    /**
     * Add "FLASHCARDS" table to schema
     * @Since 1
     */
    private static Entity addFlashcardsTable(Schema schema) {
        Entity word = schema.addEntity("FlashcardEntity");
        word.setTableName("FLASHCARDS");
        word.addIdProperty().autoincrement();
        word.addStringProperty("word").notNull();
        word.addStringProperty("definition").notNull();
        word.addIntProperty("status").notNull();
        word.implementsSerializable();
        return word;
    }

    /**
     * Create relation between "LESSONS" and "FLASHCARDS" tables
     * @Since 1
     */
    private static void bidirectionalOneToManyRelation(Entity lesson, Entity word) {
        Property lessonIdProperty = word.addLongProperty("lessonId").getProperty();
        word.addToOne(lesson, lessonIdProperty);
        lesson.addToMany(word, lessonIdProperty);
    }

}
