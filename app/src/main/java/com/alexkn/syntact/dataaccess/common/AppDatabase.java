package com.alexkn.syntact.dataaccess.common;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexkn.syntact.dataaccess.dao.BucketDao;
import com.alexkn.syntact.dataaccess.dao.LetterDao;
import com.alexkn.syntact.dataaccess.dao.PhraseDao;
import com.alexkn.syntact.dataaccess.dao.TemplateDao;
import com.alexkn.syntact.dataaccess.util.Converters;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.SolvableTranslation;
import com.alexkn.syntact.domain.model.Template;

@Database(entities = {SolvableTranslation.class, Bucket.class, Letter.class, Template.class},
        version = 18)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract PhraseDao phraseDao();

    public abstract BucketDao bucketDao();

    public abstract LetterDao letterDao();

    public abstract TemplateDao templateDao();

    public static AppDatabase getDatabase(final Context context) {

        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                                    "app_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
