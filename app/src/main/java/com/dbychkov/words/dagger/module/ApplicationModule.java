package com.dbychkov.words.dagger.module;

import android.content.Context;
import com.dbychkov.data.greendao.DaoSession;
import com.dbychkov.data.greendao.DataServiceFactory;
import com.dbychkov.data.repository.FlashcardDataRepository;
import com.dbychkov.data.repository.LessonDataRepository;
import com.dbychkov.domain.repository.FlashcardRepository;
import com.dbychkov.domain.repository.LessonRepository;
import com.dbychkov.words.app.App;
import com.dbychkov.words.bus.RxEventBus;
import com.dbychkov.words.bus.RxEventBusImpl;
import com.dbychkov.words.data.LessonsImporter;
import com.dbychkov.words.data.LessonsImporterImpl;
import com.dbychkov.words.navigator.Navigator;
import com.dbychkov.words.thread.JobExecutor;
import com.dbychkov.words.thread.PostExecutionThread;
import com.dbychkov.words.thread.PostExecutionUIThread;
import com.dbychkov.words.thread.ThreadExecutor;
import com.dbychkov.words.util.*;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(PostExecutionUIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    DaoSession providesDaoSession() {
        return DataServiceFactory.openSession(app.getApplicationContext());
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.app;
    }

    @Provides
    @Singleton
    LessonRepository provideLessonDataRepository(LessonDataRepository lessonDataRepository) {
        return lessonDataRepository;
    }

    @Provides
    @Singleton
    FlashcardRepository provideWordDataRepository(FlashcardDataRepository wordDataRepository) {
        return wordDataRepository;
    }

    @Provides
    @Singleton
    RxEventBus provideRxEventBus(RxEventBusImpl rxEventBus) {
        return rxEventBus;
    }

    @Provides
    @Singleton
    MarketService provideMarketService(MarketServiceImpl marketService) {
        return marketService;
    }

    @Provides
    @Singleton
    SpeechService provideSpeechService(SpeechServiceImpl speechService) {
        return speechService;
    }

    @Provides
    @Singleton
    ShareService provideShareService(ShareServiceImpl shareService) {
        return shareService;
    }

    @Provides
    @Singleton
    LessonsImporter provideLessonsImporter(LessonsImporterImpl lessonsImporter) {
        return lessonsImporter;
    }
}
