package com.dbychkov.words.dagger.module;

import android.app.Activity;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.dagger.PerActivity;
import com.dbychkov.words.presentation.BookmarkedLessonsTabFragmentPresenter;
import com.dbychkov.words.presentation.BundledLessonsTabFragmentPresenter;
import com.dbychkov.words.presentation.LessonsPresenter;
import com.dbychkov.words.presentation.UserLessonsTabFragmentPresenter;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
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
                        PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus){
        return new BundledLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository, rxEventBus);
    }

    @Provides
    @PerActivity
    BookmarkedLessonsTabFragmentPresenter bookmarkedLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus){
        return new BookmarkedLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository, rxEventBus);
    }

}