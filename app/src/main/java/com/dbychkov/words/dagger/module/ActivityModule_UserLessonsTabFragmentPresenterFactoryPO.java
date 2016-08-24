package com.dbychkov.words.dagger.module;

import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;

import javax.inject.Provider;

public class ActivityModule_UserLessonsTabFragmentPresenterFactoryPO {
    private final ActivityModule module;
    private final Provider<ThreadExecutor> threadExecutorProvider;
    private final Provider<PostExecutionThread> postExecutionThreadProvider;
    private final Provider<LessonRepository> lessonRepositoryProvider;
    private final Provider<RxEventBus> rxEventBusProvider;

    public ActivityModule_UserLessonsTabFragmentPresenterFactoryPO(ActivityModule module, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider, Provider<LessonRepository> lessonRepositoryProvider, Provider<RxEventBus> rxEventBusProvider) {
        this.module = module;
        this.threadExecutorProvider = threadExecutorProvider;
        this.postExecutionThreadProvider = postExecutionThreadProvider;
        this.lessonRepositoryProvider = lessonRepositoryProvider;
        this.rxEventBusProvider = rxEventBusProvider;
    }

    public ActivityModule getModule() {
        return module;
    }

    public Provider<ThreadExecutor> getThreadExecutorProvider() {
        return threadExecutorProvider;
    }

    public Provider<PostExecutionThread> getPostExecutionThreadProvider() {
        return postExecutionThreadProvider;
    }

    public Provider<LessonRepository> getLessonRepositoryProvider() {
        return lessonRepositoryProvider;
    }

    public Provider<RxEventBus> getRxEventBusProvider() {
        return rxEventBusProvider;
    }
}
