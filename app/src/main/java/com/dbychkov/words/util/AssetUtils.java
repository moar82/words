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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading assets
 */
public class AssetUtils {

    public static String readAssetAsString(Context context, String file) {
        return readStringAsset(context, file, new Strategy<String>() {
            @Override
            public String execute(final BufferedReader reader) throws IOException {
                StringBuilder buf = new StringBuilder();
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    buf.append(mLine);
                }
                return buf.toString();
            }
        });
    }

    public static List<String> readAssetAsStringList(Context context, String file) {
        return readStringAsset(context, file, new Strategy<List<String>>() {
            @Override
            public List<String> execute(final BufferedReader reader) throws IOException {
                List<String> result = new ArrayList<>();
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    result.add(mLine);
                }
                return result;
            }
        });
    }

    public static <T> T readStringAsset(Context context, String file, Strategy<T> strategy) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(file)));
            return strategy.execute(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
    }

    interface Strategy<T> {
        T execute(BufferedReader reader) throws IOException;
    }

}
