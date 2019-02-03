package com.alexkn.syntact.dataaccess.common;

import android.content.Context;

import com.alexkn.syntact.dataaccess.phrase.api.PhraseEntity;
import com.alexkn.syntact.dataaccess.phrase.api.PhraseDao;
import com.alexkn.syntact.dataaccess.util.Converters;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PhraseEntity.class}, version = 3)
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
                                    "app_database").fallbackToDestructiveMigration()
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
                    //TODO different databases for locales
                }
            }
        }
        return instance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //no migration
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE Phrase ADD COLUMN clueLocale TEXT NOT NULL DEFAULT 'en'");
            database.execSQL(
                    "ALTER TABLE Phrase ADD COLUMN solutionLocale TEXT NOT NULL DEFAULT 'de'");
        }
    };
}
