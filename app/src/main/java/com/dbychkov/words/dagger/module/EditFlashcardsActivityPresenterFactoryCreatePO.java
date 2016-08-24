package com.dbychkov.words.dagger.module;

import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.SpeechService;

import javax.inject.Provider;

public class EditFlashcardsActivityPresenterFactoryCreatePO {
    private final ActivityModule module;
    private final Provider<ThreadExecutor> threadExecutorProvider;
    private final Provider<PostExecutionThread> postExecutionThreadProvider;
    private final Provider<FlashcardRepository> flashcardRepositoryProvider;
    private final Provider<SpeechService> speechServiceProvider;

    public EditFlashcardsActivityPresenterFactoryCreatePO(ActivityModule module, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider, Provider<FlashcardRepository> flashcardRepositoryProvider, Provider<SpeechService> speechServiceProvider) {
        this.module = module;
        this.threadExecutorProvider = threadExecutorProvider;
        this.postExecutionThreadProvider = postExecutionThreadProvider;
        this.flashcardRepositoryProvider = flashcardRepositoryProvider;
        this.speechServiceProvider = speechServiceProvider;
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

    public Provider<FlashcardRepository> getFlashcardRepositoryProvider() {
        return flashcardRepositoryProvider;
    }

    public Provider<SpeechService> getSpeechServiceProvider() {
        return speechServiceProvider;
    }
}
