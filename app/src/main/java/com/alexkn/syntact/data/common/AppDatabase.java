package com.alexkn.syntact.data.common;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexkn.syntact.data.dao.BucketDao;
import com.alexkn.syntact.data.dao.ClueDao;
import com.alexkn.syntact.data.dao.LetterDao;
import com.alexkn.syntact.data.dao.SolvableItemDao;
import com.alexkn.syntact.data.util.Converters;
import com.alexkn.syntact.data.model.Bucket;
import com.alexkn.syntact.data.model.Clue;
import com.alexkn.syntact.data.model.Letter;
import com.alexkn.syntact.data.model.SolvableItem;
import com.alexkn.syntact.data.model.views.BucketDetail;

@Database(entities = {SolvableItem.class, Bucket.class, Letter.class, Clue.class},views = {BucketDetail.class},
        version = 30)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract SolvableItemDao solvableItemDao();

    public abstract BucketDao bucketDao();

    public abstract LetterDao letterDao();

    public abstract ClueDao clueDao();

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
