package com.example.sstep.todo.checklist;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckListViewModel2 extends ViewModel {

    private MutableLiveData<String> dateLiveData = new MutableLiveData<>();


    public void setDate(String date) {
        dateLiveData.setValue(date);
    }

    public LiveData<String> getDate() {
        return dateLiveData;
    }

}