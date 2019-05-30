package com.alexkn.syntact.presentation.template.list;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alexkn.syntact.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.apache.commons.lang3.StringUtils;

public class TemplateCreateFragment extends Fragment {

    private TemplateCreateViewModel viewModel;

    private EditText wordsTextView;
    private EditText nameTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.template_create_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(TemplateCreateViewModel.class);

        wordsTextView = view.findViewById(R.id.createTemplateEditText);
        nameTextView = view.findViewById(R.id.nameEditText);

        ExtendedFloatingActionButton fab = view.findViewById(R.id.createTemplateFab);

        fab.setOnClickListener(this::createTemplate);
        return view;
    }

    public void createTemplate(View view) {

        String string = wordsTextView.getText().toString();
        String name = nameTextView.getText().toString();

        String[] words = StringUtils.split(string);

        viewModel.createTemplate(name, words);

        Navigation.findNavController(view).popBackStack();

    }
}
