package com.example.boutiqueshop.ui.configuraciones;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfiguracionesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConfiguracionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Configuraciones");
    }

    public LiveData<String> getText() {
        return mText;
    }
}