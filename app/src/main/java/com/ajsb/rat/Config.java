package com.ajsb.rat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Config extends ViewModel {
    private MutableLiveData<Integer> limite_tiempo= new MutableLiveData<>();

    public Integer getLimite(){
        return this.limite_tiempo.getValue();
    }
    public void setLimite(int limite){

        this.limite_tiempo.setValue(limite);
    }
}
