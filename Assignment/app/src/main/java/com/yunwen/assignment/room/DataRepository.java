package com.yunwen.assignment.room;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

class DataRepository {

    private DataDao mDataDao;
    private LiveData<List<Data>> mAllWords;

    DataRepository(Application application) {
        DataRoomDatabase db = DataRoomDatabase.getDatabase(application);
        mDataDao = db.dataDao();
        mAllWords = mDataDao.getAll();
    }

    LiveData<List<Data>> getAllWords() {
        return mAllWords;
    }

    void insert(Data data) {
        new insertAsyncTask(mDataDao).execute(data);
    }

    private static class insertAsyncTask extends AsyncTask<Data, Void, Void> {

        private DataDao mAsyncTaskDao;

        insertAsyncTask(DataDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Data... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
