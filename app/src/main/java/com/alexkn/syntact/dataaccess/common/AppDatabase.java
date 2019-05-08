package com.alexkn.syntact.dataaccess.common;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexkn.syntact.dataaccess.language.LanguagePairDao;
import com.alexkn.syntact.dataaccess.letter.LetterDao;
import com.alexkn.syntact.dataaccess.phrase.PhraseDao;
import com.alexkn.syntact.dataaccess.util.Converters;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.Phrase;

@Database(entities = {Phrase.class, LanguagePair.class, Letter.class}, version = 10)
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
                }
            }
        }
        return instance;
    }


}
