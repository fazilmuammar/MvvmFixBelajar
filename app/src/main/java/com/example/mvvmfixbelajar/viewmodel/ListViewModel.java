package com.example.mvvmfixbelajar.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmfixbelajar.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countriesLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public void refresh(){
        fetchCountries();
    }

    public void fetchCountries(){
        CountryModel country1 = new CountryModel("Indonesia","Jakarta","");
        CountryModel country2 = new CountryModel("Japan","Tokyo","");
        CountryModel country3 = new CountryModel("Malaysia","Kuala Lumpur","");

        List<CountryModel> list = new ArrayList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);

        countries.setValue(list);
        countriesLoadError.setValue(false);
        loading.setValue(false);
    }
}
