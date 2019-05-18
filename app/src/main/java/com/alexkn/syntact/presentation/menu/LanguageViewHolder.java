package com.alexkn.syntact.presentation.menu;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.presentation.hangman.ListItemViewHolder;

import java.util.Locale;

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

        String languageFrom = bucket.getLanguageLeft().getDisplayLanguage();
        String languageTo = bucket.getLanguageRight().getDisplayLanguage();

        languageLabel.setText(languageFrom + " - " + languageTo);

        itemView.setOnClickListener(this::startHangman);
    }

    private void startHangman(View view) {

        MainMenuFragmentDirections.ActionMainMenuFragmentToHangmanBoardFragment action = MainMenuFragmentDirections
                .actionMainMenuFragmentToHangmanBoardFragment(bucket.getId());
        Navigation.findNavController(view).navigate(action);
    }
}
