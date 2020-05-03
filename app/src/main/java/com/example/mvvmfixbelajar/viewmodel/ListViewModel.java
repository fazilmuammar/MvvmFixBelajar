package com.example.mvvmfixbelajar.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmfixbelajar.model.CountriesService;
import com.example.mvvmfixbelajar.model.CountryModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countriesLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public void refresh(){
        fetchCountries();
    }

    private CountriesService countriesService = CountriesService.getInstance();

    private CompositeDisposable disposable = new CompositeDisposable();



    public void fetchCountries(){

        loading.setValue(true);
        disposable.add(
                countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        countriesLoadError.setValue(false);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        countriesLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );

        /*Dummy Data
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
         */



    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
