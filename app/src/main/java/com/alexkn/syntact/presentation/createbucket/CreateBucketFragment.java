package com.alexkn.syntact.presentation.createbucket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.restservice.Template;

import java.util.List;
import java.util.Locale;

public class CreateBucketFragment extends Fragment {

    private static final String TAG = CreateBucketFragment.class.getSimpleName();

    private CreateBucketViewModel viewModel;

    private View addButton;

    private Locale selectedLanguage;

    private Template selectedTemplate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bucket_create_fragment, container, false);
        viewModel = ViewModelProviders
                .of(this, ((ApplicationComponentProvider) getActivity().getApplication()).getApplicationComponent().createBucketViewModelFactory())
                .get(CreateBucketViewModel.class);

        addButton = view.findViewById(R.id.addBucketButton);
        EditText words = view.findViewById(R.id.editText);

        List<Locale> dataset = viewModel.getAvailableBuckets();
        dataset.remove(Locale.getDefault());
        selectedLanguage = dataset.get(0);

        RecyclerView languageRecyclerView = view.findViewById(R.id.selectLanguageRecyclerView);
        LinearLayoutManager languageLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        languageRecyclerView.setLayoutManager(languageLayoutManager);
        ChooseLanguageAdapter adapter = new ChooseLanguageAdapter(dataset);
        languageRecyclerView.setAdapter(adapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(languageRecyclerView);
        languageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = languageLayoutManager.findFirstVisibleItemPosition();
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        selectedLanguage = adapter.getItemAt(itemPosition);
                    }
                }
            }
        });

        RecyclerView templateRecyclerView = view.findViewById(R.id.selectTemplateRecyclerView);
        LinearLayoutManager templateLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        templateRecyclerView.setLayoutManager(templateLayoutManager);

        ChooseTemplateAdapter chooseTemplateAdapter = new ChooseTemplateAdapter();
        templateRecyclerView.setAdapter(chooseTemplateAdapter);
        LinearSnapHelper linearSnapHelper1 = new LinearSnapHelper();
        linearSnapHelper1.attachToRecyclerView(templateRecyclerView);

        templateRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = templateLayoutManager.findFirstVisibleItemPosition();
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        selectedTemplate = chooseTemplateAdapter.getItemAt(itemPosition);
                    }
                }
            }
        });

        addButton.setOnClickListener(v -> {

            viewModel.addBucket(selectedLanguage, selectedTemplate, words.getText().toString());//TODO
            Navigation.findNavController(view).popBackStack();
        });

        viewModel.getAvailableTemplates().observe(getViewLifecycleOwner(), chooseTemplateAdapter::setData);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CreateBucketViewModel.class);
    }
}
