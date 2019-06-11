package com.alexkn.syntact.presentation.bucket.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

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

import java.util.List;
import java.util.Locale;

public class CreateBucketFragment extends Fragment {

    private static final String TAG = CreateBucketFragment.class.getSimpleName();

    private CreateBucketViewModel viewModel;

    private View addButton;

    private Locale selectedLanguage;

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

        RecyclerView recyclerView = view.findViewById(R.id.selectLanguageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ChooseLanguageAdapter adapter = new ChooseLanguageAdapter(dataset);
        recyclerView.setAdapter(adapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        selectedLanguage = adapter.getItemAt(itemPosition);
                    }
                }
            }
        });


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
