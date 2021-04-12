package com.example.rankingu.ui.Rankingtons;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RankingtonsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RankingtonsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Recomendaciones del horario");
    }

    public LiveData<String> getText() {
        return mText;
    }
}