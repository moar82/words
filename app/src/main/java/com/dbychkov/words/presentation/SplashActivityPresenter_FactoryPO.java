package com.dbychkov.words.presentation;

import android.content.Context;

import com.dbychkov.words.data.LessonsImporter;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;

import javax.inject.Provider;

import dagger.MembersInjector;

public class SplashActivityPresenter_FactoryPO {
    private final MembersInjector<SplashActivityPresenter> membersInjector;
    private final Provider<ThreadExecutor> threadExecutorProvider;
    private final Provider<PostExecutionThread> postExecutionThreadProvider;
    private final Provider<LessonsImporter> lessonImporterProvider;
    private final Provider<Context> contextProvider;

    public SplashActivityPresenter_FactoryPO(MembersInjector<SplashActivityPresenter> membersInjector, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider, Provider<LessonsImporter> lessonImporterProvider, Provider<Context> contextProvider) {
        this.membersInjector = membersInjector;
        this.threadExecutorProvider = threadExecutorProvider;
        this.postExecutionThreadProvider = postExecutionThreadProvider;
        this.lessonImporterProvider = lessonImporterProvider;
        this.contextProvider = contextProvider;
    }

    public MembersInjector<SplashActivityPresenter> getMembersInjector() {
        return membersInjector;
    }

    public Provider<ThreadExecutor> getThreadExecutorProvider() {
        return threadExecutorProvider;
    }

    public Provider<PostExecutionThread> getPostExecutionThreadProvider() {
        return postExecutionThreadProvider;
    }

    public Provider<LessonsImporter> getLessonImporterProvider() {
        return lessonImporterProvider;
    }

    public Provider<Context> getContextProvider() {
        return contextProvider;
    }
}
