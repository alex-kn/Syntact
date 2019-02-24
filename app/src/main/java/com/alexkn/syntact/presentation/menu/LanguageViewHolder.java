package com.alexkn.syntact.presentation.menu;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.presentation.hangman.ListItemViewHolder;

import org.jetbrains.annotations.Contract;

import java.util.Locale;

import androidx.navigation.Navigation;

public class LanguageViewHolder extends ListItemViewHolder<LanguagePair> {

    private final TextView scoreLabel;

    private final TextView languageLabel;

    private final TextView levelLabel;

    private final View startButton;

    private final View statsButton;

    LanguagePair languagePair;

    LanguageViewHolder(View v) {

        super(v);
        scoreLabel = v.findViewById(R.id.langScoreLabel);
        levelLabel = v.findViewById(R.id.langLevelLabel);
        languageLabel = v.findViewById(R.id.langLabel);

        startButton = v.findViewById(R.id.langStartButton);
        statsButton = v.findViewById(R.id.langStatsButton);
    }

    @Override
    public void bindTo(LanguagePair languagePair) {

        this.languagePair = languagePair;

        String language;
        if (Locale.getDefault().getLanguage()
                .equals(languagePair.getLanguageLeft().getLanguage())) {
            language = languagePair.getLanguageRight().getDisplayLanguage();
        } else {
            language = languagePair.getLanguageLeft().getDisplayLanguage();
        }
        languageLabel.setText(language);

        scoreLabel.setText(String.valueOf(languagePair.getScore()));
        levelLabel.setText(String.valueOf(languagePair.getScore()/100));

        startButton.setOnClickListener(this::startHangman);

    }

    private void startHangman(View view) {
        MainMenuFragmentDirections.ActionMainMenuFragmentToHangmanBoardFragment action = MainMenuFragmentDirections
                .actionMainMenuFragmentToHangmanBoardFragment();
        action.setLanguagePairId(languagePair.getId());
        Navigation.findNavController(view).navigate(action);
    }
}
