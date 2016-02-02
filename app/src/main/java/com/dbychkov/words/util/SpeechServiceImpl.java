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

package com.dbychkov.words.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Text to speech service
 */
@Singleton
public class SpeechServiceImpl implements SpeechService{

    public static final float DEFAULT_SPEECH_RATE = 0.8f;

    private TextToSpeech textToSpeech;

    @Inject
    public SpeechServiceImpl(Context context) {
        textToSpeech = new TextToSpeech(context,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                    }
                });
        textToSpeech.setLanguage(Locale.US);
        textToSpeech.setSpeechRate(DEFAULT_SPEECH_RATE);
    }

    @Override
    public void speak(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
