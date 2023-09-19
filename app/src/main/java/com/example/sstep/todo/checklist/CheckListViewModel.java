package com.example.sstep.todo.checklist;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckListViewModel extends ViewModel {
    private MutableLiveData<Long> categoryIdLiveData = new MutableLiveData<>();
    /*

    private MutableLiveData<String> dateLiveData = new MutableLiveData<>();


     */

    public void setCategoryIdLiveData(long staffId) {
        categoryIdLiveData.setValue(staffId);
    }

    public LiveData<Long> getCategoryIdLiveData() {
        return categoryIdLiveData;
    }
    /*

    public void setDate(String date) {
        dateLiveData.setValue(date);
    }

    public LiveData<String> getDate() {
        return dateLiveData;
    }

     */

}