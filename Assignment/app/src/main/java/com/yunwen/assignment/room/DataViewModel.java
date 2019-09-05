package com.yunwen.assignment.room;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<List<Data>> listLiveData;

    public DataViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        listLiveData = mRepository.getAllWords();
    }

    public LiveData<List<Data>> getAllData() {
        return listLiveData;
    }

    public void insert(Data dataModel) {
        mRepository.insert(dataModel);
    }
}
