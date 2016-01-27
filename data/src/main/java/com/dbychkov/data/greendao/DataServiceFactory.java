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

package com.dbychkov.data.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataServiceFactory {

    private static final String DATABASE_NAME = "words.db";

    private static SQLiteDatabase database;

    public static DaoSession openSession(Context context) {
        if (database == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
            database = helper.getWritableDatabase();
        }
        DaoMaster daoMaster = new DaoMaster(database);
        return daoMaster.newSession();
    }

}