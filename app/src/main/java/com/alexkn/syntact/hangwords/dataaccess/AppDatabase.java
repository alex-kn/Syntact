package com.alexkn.syntact.hangwords.dataaccess;

import android.content.Context;

import com.alexkn.syntact.hangwords.dataaccess.api.to.Phrase;
import com.alexkn.syntact.hangwords.dataaccess.api.PhraseDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Phrase.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract PhraseDao phraseDao();

    static AppDatabase getDatabase(final Context context) {

        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                                    "app_database").build();
                }
            }
        }
        return instance;
    }
}
