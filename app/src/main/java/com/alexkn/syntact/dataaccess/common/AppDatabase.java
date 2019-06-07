package com.alexkn.syntact.dataaccess.common;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexkn.syntact.dataaccess.dao.AttemptDao;
import com.alexkn.syntact.dataaccess.dao.BucketDao;
import com.alexkn.syntact.dataaccess.dao.ClueDao;
import com.alexkn.syntact.dataaccess.dao.LetterDao;
import com.alexkn.syntact.dataaccess.dao.SolvableItemDao;
import com.alexkn.syntact.dataaccess.util.Converters;
import com.alexkn.syntact.domain.model.Attempt;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.SolvableItem;

@Database(entities = {SolvableItem.class, Bucket.class, Letter.class, Clue.class, Attempt.class},
        version = 26)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract SolvableItemDao solvableItemDao();

    public abstract BucketDao bucketDao();

    public abstract LetterDao letterDao();

    public abstract ClueDao clueDao();

    public abstract AttemptDao attemptDao();

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
