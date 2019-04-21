package com.alexkn.syntact.presentation.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.presentation.hangman.ListItemAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LanguageAdapter extends ListItemAdapter<LanguagePair, LanguageViewHolder> {

    private ViewModelCallback viewModelCallback;

    public LanguageAdapter(ViewModelCallback viewModelCallback) {

        this.viewModelCallback = viewModelCallback;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_language_card, parent, false);

        LanguageViewHolder viewHolder = new LanguageViewHolder(view);
        view.setOnLongClickListener(v -> {

            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                LanguagePair languagePair = getList().get(adapterPosition);
                viewModelCallback.delete(languagePair);
            }
            return true;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {

        holder.bindTo(getList().get(position));
    }
}
