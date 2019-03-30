package com.alexkn.syntact.dataaccess.common;

import android.content.Context;

import com.alexkn.syntact.dataaccess.language.LanguagePairDao;
import com.alexkn.syntact.dataaccess.language.LanguagePairEntity;
import com.alexkn.syntact.dataaccess.letter.LetterDao;
import com.alexkn.syntact.dataaccess.letter.LetterEntity;
import com.alexkn.syntact.dataaccess.phrase.PhraseDao;
import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.dataaccess.util.Converters;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PhraseEntity.class, LanguagePairEntity.class, LetterEntity.class}, version = 9)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract PhraseDao phraseDao();

    public abstract LanguagePairDao languagePairDao();

    public abstract LetterDao letterDao();

    public static AppDatabase getDatabase(final Context context) {

        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                                    "app_database")
                            .fallbackToDestructiveMigration().build();
                    //TODO different databases for locales
                }
            }
        }
        return instance;
    }


}
