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

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static final String SEPARATOR = "\\|";

    public static String listToString(List<String> list) {
        return TextUtils.join("|", list);
    }

    public static String arrayToString(List<String> list) {
        return TextUtils.join("|", list);
    }

    public static List<String> stringToList(String str) {
        return new ArrayList<>(Arrays.asList(str.split(SEPARATOR)));
    }

    public static String[] stringToArray(String str) {
        return str.split(SEPARATOR);
    }

}
