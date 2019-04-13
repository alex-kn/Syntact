package com.alexkn.syntact.presentation.addlanguage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alexkn.syntact.R;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class AddLanguageFragment extends Fragment {

    private AddLanguageViewModel viewModel;

    private Locale[] languages = new Locale[]{Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH,
            Locale.ITALIAN};

    private View addButton;

    private Spinner leftSpinner;

    private Spinner rightSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_language_fragment, container, false);

        addButton = view.findViewById(R.id.addButton);
        leftSpinner = view.findViewById(R.id.langaugeLeftSpinner);
        rightSpinner = view.findViewById(R.id.langaugeRightSpinner);

        Context context = Objects.requireNonNull(getContext());
        ArrayAdapter<Locale> adapterLeft = new ArrayAdapter<>(context, R.layout.add_language_spinner_label,
                languages);
        ArrayAdapter<Locale> adapterRight = new ArrayAdapter<>(context, R.layout.add_language_spinner_label,
                languages);
        leftSpinner.setAdapter(adapterLeft);
        rightSpinner.setAdapter(adapterRight);

        AdapterView.OnItemSelectedListener listener = new OnLanguageSelectedItemListener();
        leftSpinner.setOnItemSelectedListener(listener);
        rightSpinner.setOnItemSelectedListener(listener);

        addButton.setOnClickListener(v -> {
            viewModel.addLanguage((Locale) leftSpinner.getSelectedItem(), (Locale) rightSpinner.getSelectedItem());
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AddLanguageViewModel.class);
    }

    private class OnLanguageSelectedItemListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            checkValidLanguages();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

            checkValidLanguages();
        }

        private void checkValidLanguages() {

            Locale localeLeft = (Locale) leftSpinner.getSelectedItem();
            Locale localeRight = (Locale) rightSpinner.getSelectedItem();
            addButton.setEnabled(!localeLeft.equals(localeRight));
        }
    }
}
