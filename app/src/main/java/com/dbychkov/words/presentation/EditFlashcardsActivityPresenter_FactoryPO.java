package com.dbychkov.words.presentation;

import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.SpeechService;

import javax.inject.Provider;

import dagger.MembersInjector;

public class EditFlashcardsActivityPresenter_FactoryPO {
    private final MembersInjector<EditFlashcardsActivityPresenter> membersInjector;
    private final Provider<ThreadExecutor> threadExecutorProvider;
    private final Provider<PostExecutionThread> postExecutionThreadProvider;
    private final Provider<FlashcardRepository> flashcardRepositoryProvider;
    private final Provider<SpeechService> speechServiceProvider;

    public EditFlashcardsActivityPresenter_FactoryPO(MembersInjector<EditFlashcardsActivityPresenter> membersInjector, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider, Provider<FlashcardRepository> flashcardRepositoryProvider, Provider<SpeechService> speechServiceProvider) {
        this.membersInjector = membersInjector;
        this.threadExecutorProvider = threadExecutorProvider;
        this.postExecutionThreadProvider = postExecutionThreadProvider;
        this.flashcardRepositoryProvider = flashcardRepositoryProvider;
        this.speechServiceProvider = speechServiceProvider;
    }

    public MembersInjector<EditFlashcardsActivityPresenter> getMembersInjector() {
        return membersInjector;
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
