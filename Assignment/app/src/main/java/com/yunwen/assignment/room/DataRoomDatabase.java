package com.yunwen.assignment.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Data.class}, version = 1, exportSchema = false)
public abstract class DataRoomDatabase extends RoomDatabase {

    public abstract DataDao dataDao();

    private static volatile DataRoomDatabase INSTANCE;

    public static DataRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataRoomDatabase.class, "data_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DataDao mDao;

        PopulateDbAsync(DataRoomDatabase db) {
            mDao = db.dataDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }

}
