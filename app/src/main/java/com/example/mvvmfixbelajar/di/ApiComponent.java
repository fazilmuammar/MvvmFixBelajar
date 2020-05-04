package com.example.mvvmfixbelajar.di;

import com.example.mvvmfixbelajar.model.CountriesService;
import com.example.mvvmfixbelajar.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules =  {ApiModule.class})
public interface ApiComponent {

    void inject(CountriesService service);

    void injectCountriesService(ListViewModel viewModel);
}
