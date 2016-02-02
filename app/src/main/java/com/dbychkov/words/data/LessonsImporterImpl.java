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

package com.dbychkov.words.data;

import android.content.Context;
import com.dbychkov.domain.Flashcard;
import com.dbychkov.domain.Lesson;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.util.AssetUtils;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Importer service. Provides initial initialization of database from json file
 */
@Singleton
public class LessonsImporterImpl implements LessonsImporter {

    public static final String LESSONS_JSON = "lessons.json";

    private final FlashcardRepository flashcardRepository;
    private final LessonRepository lessonRepository;
    private final Context context;

    @Inject
    public LessonsImporterImpl(FlashcardRepository flashcardRepository, LessonRepository lessonRepository,
            Context context) {
        this.flashcardRepository = flashcardRepository;
        this.lessonRepository = lessonRepository;
        this.context = context;
    }

    @Override
    public void importLessons() {
        final List<Lesson> lessonsForBulkInsert = new ArrayList<>();
        final List<Flashcard> wordsForBulkInsert = new ArrayList<>();
        final Gson gson = new Gson();

        RootInFile rootInFile = gson
                .fromJson(AssetUtils.readAssetAsString(context, LESSONS_JSON), RootInFile.class);
        for (LessonInFile lessonInFile : rootInFile.lessons) {

            Lesson lessonEntity = new Lesson();
            lessonEntity.setLessonName(lessonInFile.lessonName);
            lessonEntity.setUserLesson(false);
            lessonEntity.setId(Long.valueOf(lessonInFile.lessonId));
            lessonEntity.setImagePath(lessonInFile.image);
            lessonsForBulkInsert.add(lessonEntity);

            for (String entry : AssetUtils.readAssetAsStringList(context, lessonInFile.flashcards)) {
                String[] wordDefinition = entry.split(",");

                Flashcard flashcardEntry = new Flashcard();
                flashcardEntry.setLessonId(Long.valueOf(lessonInFile.lessonId));
                flashcardEntry.setDefinition(wordDefinition[1]);
                flashcardEntry.setWord(wordDefinition[0]);
                flashcardEntry.setStatus(0);
                wordsForBulkInsert.add(flashcardEntry);
            }
        }
        lessonRepository.bulkInsertLessons(lessonsForBulkInsert).subscribe();
        flashcardRepository.bulkInsertFlashcards(wordsForBulkInsert).subscribe();
    }

    private static class RootInFile {
        public List<LessonInFile> lessons;
    }

    private static class LessonInFile {
        public int lessonId;
        public String lessonName;
        public String image;
        public String flashcards;
    }

}

