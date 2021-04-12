package com.example.rankingu.ui.Error;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ErrorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ErrorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}