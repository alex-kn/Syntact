package com.alexkn.syntact.presentation.menu;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

import androidx.navigation.Navigation;

public class LanguageViewHolder extends ListItemViewHolder<Bucket> {

    private final TextView languageLabel;

    private Bucket bucket;

    LanguageViewHolder(View v) {

        super(v);
        languageLabel = v.findViewById(R.id.langLabel);
    }

    @Override
    public void bindTo(Bucket bucket) {

        this.bucket = bucket;

        languageLabel.setText("Bucket");

        itemView.setOnClickListener(this::startHangman);
    }

    private void startHangman(View view) {

        MainMenuFragmentDirections.ActionMainMenuFragmentToHangmanBoardFragment action = MainMenuFragmentDirections
                .actionMainMenuFragmentToHangmanBoardFragment(bucket.getId());
        Navigation.findNavController(view).navigate(action);
    }
}
