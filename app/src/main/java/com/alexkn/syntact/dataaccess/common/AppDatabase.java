package com.alexkn.syntact.dataaccess.common;

import android.content.ContentProvider;
import android.content.Context;

import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.dataaccess.phrase.PhraseDao;
import com.alexkn.syntact.dataaccess.util.Converters;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PhraseEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract PhraseDao phraseDao();

    public static AppDatabase getDatabase(final Context context) {

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
