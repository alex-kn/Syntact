package com.alexkn.syntact.presentation.template.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alexkn.syntact.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TemplateFragment extends Fragment {

    private TemplateViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.template_fragment, container, false);
        RecyclerView templateList = view.findViewById(R.id.templateList);
        templateList.setLayoutManager(new LinearLayoutManager(getContext()));

        TemplateAdapter adapter = new TemplateAdapter();
        templateList.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.templateRefreshLayout);

        viewModel.getTemplates().observe(getViewLifecycleOwner(), data -> {
            adapter.submitList(data);
            swipeRefreshLayout.setRefreshing(false);
        });

        FloatingActionButton button = view.findViewById(R.id.newTemplateFab);
        button.setOnClickListener(this::newTemplate);

        swipeRefreshLayout.setOnRefreshListener(viewModel::refreshTemplates);

        return view;
    }

    private void newTemplate(View view) {

        NavDirections navDirections = TemplateFragmentDirections
                .actionTemplateFragmentToTemplateCreateFragment();
        Navigation.findNavController(view).navigate(navDirections);
    }

}
