package com.alexkn.syntact.dataaccess.common;

import android.content.Context;

import com.alexkn.syntact.dataaccess.language.LanguagePairDao;
import com.alexkn.syntact.dataaccess.language.LanguagePairEntity;
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

@Database(entities = {PhraseEntity.class, LanguagePairEntity.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract PhraseDao phraseDao();

    public abstract LanguagePairDao languagePairDao();

    public static AppDatabase getDatabase(final Context context) {

        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                                    "app_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4,
                                    MIGRATION_4_5,MIGRATION_5_6).fallbackToDestructiveMigration().build();
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

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `ActiveLanguagePair` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `languageLeft` TEXT NOT NULL, `languageRight` TEXT NOT NULL)");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL(
                    "ALTER TABLE ActiveLanguagePair ADD COLUMN score INTEGER NOT NULL DEFAULT 0");
            database.execSQL(
                    "ALTER TABLE ActiveLanguagePair RENAME TO LanguagePair");

        }
    };
    private static final Migration MIGRATION_5_6 = new Migration(5,6) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
}
