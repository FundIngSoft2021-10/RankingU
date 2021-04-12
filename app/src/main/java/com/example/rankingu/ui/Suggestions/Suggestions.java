
package com.example.rankingu.ui.Suggestions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Suggestions extends ViewModel {

    private MutableLiveData<String> mText;

    public Suggestions() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}