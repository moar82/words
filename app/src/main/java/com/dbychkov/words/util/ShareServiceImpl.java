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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Service, which helps user to tell others about the app with the help of "Share" intent
 */
@Singleton
public class ShareServiceImpl implements ShareService {

    private static final String DEFAULT_TEXT = "Check out this app on google play\n";

    private Context context;

    @Inject
    public ShareServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void shareAsPlainText(Activity activity) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(
                Intent.EXTRA_TEXT,
                DEFAULT_TEXT
                        + Uri.parse("http://play.google.com/store/apps/details?id="
                        + context.getPackageName()));

        activity.startActivity(Intent.createChooser(intent, "Share"));
    }

}
