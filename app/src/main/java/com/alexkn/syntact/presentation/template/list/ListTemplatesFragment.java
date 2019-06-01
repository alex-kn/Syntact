package com.alexkn.syntact.presentation.template.list;

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

public class ListTemplatesFragment extends Fragment {

    private ListTemplatesViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.template_list_fragment, container, false);
        RecyclerView templateList = view.findViewById(R.id.templateList);
        templateList.setLayoutManager(new LinearLayoutManager(getContext()));

        TemplateAdapter adapter = new TemplateAdapter();
        templateList.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(ListTemplatesViewModel.class);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.templateRefreshLayout);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_surface);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_primary, R.color.color_secondary);

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

        NavDirections navDirections = ListTemplatesFragmentDirections
                .actionTemplateFragmentToTemplateCreateFragment();
        Navigation.findNavController(view).navigate(navDirections);
    }
}
