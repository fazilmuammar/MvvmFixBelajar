package com.example.mvvmfixbelajar.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvvmfixbelajar.R;
import com.example.mvvmfixbelajar.model.CountryModel;
import com.example.mvvmfixbelajar.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvError;
    RecyclerView listCountry;
    ProgressBar progressBar;

    private ListViewModel viewModel;
    private CountryListAdapter adapter = new CountryListAdapter(new ArrayList<CountryModel>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe);
        tvError = findViewById(R.id.textViewError);
        listCountry = findViewById(R.id.listCountries);
        progressBar = findViewById(R.id.progressBar);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();
        listCountry.setLayoutManager(new LinearLayoutManager(this));
        listCountry.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        observerViewModel();

    }

    private void observerViewModel(){
        viewModel.countries.observe(this, new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if (countryModels != null){
                    listCountry.setVisibility(View.VISIBLE);
                    adapter.updateCountries(countryModels);
                }
            }
        });

        viewModel.countriesLoadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null){
                    tvError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }
            }
        });

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null){
                    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading){
                        tvError.setVisibility(View.GONE);
                        listCountry.setVisibility(View.GONE);
                    }

                }

            }
        });


    }

}
