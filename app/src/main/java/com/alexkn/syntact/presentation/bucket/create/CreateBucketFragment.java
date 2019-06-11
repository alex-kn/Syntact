package com.alexkn.syntact.presentation.bucket.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.presentation.play.menu.PlayMenuViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateBucketFragment extends Fragment {

    private static final String TAG = CreateBucketFragment.class.getSimpleName();

    private CreateBucketViewModel viewModel;

    private View addButton;

    private Locale selectedLanguage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bucket_create_fragment, container, false);
        viewModel =ViewModelProviders.of(this, ((ApplicationComponentProvider) getActivity().getApplication()).getApplicationComponent().createBucketViewModelFactory())
                .get(CreateBucketViewModel.class);

        addButton = view.findViewById(R.id.addBucketButton);
        EditText words = view.findViewById(R.id.editText);

        List<Locale> languages = viewModel.getAvailableBuckets();
        List<List<Locale>> dataset = Arrays.asList(languages);
        selectedLanguage = languages.get(0);

        RecyclerView recyclerView = view.findViewById(R.id.bucketOptionRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new CreateBucketOptionsAdapter(dataset, (integer, locale) -> {
            if (integer == 0) {
                selectedLanguage = locale;
            }
        }));

        NumberPicker np = view.findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(10);

        addButton.setOnClickListener(v -> {

            viewModel.addBucket(selectedLanguage, null, words.getText().toString());//TODO
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CreateBucketViewModel.class);
    }
}
