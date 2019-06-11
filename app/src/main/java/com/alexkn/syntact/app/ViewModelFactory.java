package com.alexkn.syntact.app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory<VM extends ViewModel> implements ViewModelProvider.Factory {

    private Provider<VM> viewModel;

    @Inject
    public ViewModelFactory(Provider<VM> viewModel) {

        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) viewModel.get();
    }
}
