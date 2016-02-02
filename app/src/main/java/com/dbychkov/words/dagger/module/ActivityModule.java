package com.dbychkov.words.dagger.module;

import android.app.Activity;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.dagger.PerActivity;
import com.dbychkov.words.presentation.*;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.SpeechService;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return activity;
    }

    @Provides
    @PerActivity
    UserLessonsTabFragmentPresenter userLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        return new UserLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository, rxEventBus);
    }

    @Provides
    @PerActivity
    BundledLessonsTabFragmentPresenter bundledLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        return new BundledLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository,
                rxEventBus);
    }

    @Provides
    @PerActivity
    BookmarkedLessonsTabFragmentPresenter bookmarkedLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        return new BookmarkedLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository,
                rxEventBus);
    }

    @Provides
    @PerActivity
    ViewFlashcardsActivityPresenter viewFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        return new ViewFlashcardsActivityPresenter(threadExecutor, postExecutionThread, flashcardRepository,
                speechService);
    }

    @Provides
    @PerActivity
    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        return new EditFlashcardsActivityPresenter(threadExecutor, postExecutionThread, flashcardRepository,
                speechService);
    }

}