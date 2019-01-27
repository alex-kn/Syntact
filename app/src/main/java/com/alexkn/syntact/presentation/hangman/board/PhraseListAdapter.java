package com.alexkn.syntact.presentation.hangman.board;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhraseListAdapter extends ListItemAdapter<Phrase, PhraseViewHolder> {

    private ViewModelCallback viewModelCallback;

    public PhraseListAdapter(ViewModelCallback viewModelCallback) {

        this.viewModelCallback = viewModelCallback;
    }

    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.phrase_card, parent, false);
        PhraseViewHolder viewHolder = new PhraseViewHolder(view);

        viewHolder.itemView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Letter letter = (Letter) event.getLocalState();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Phrase solvablePhrase = this.getList().get(adapterPosition);
                    return viewModelCallback.handleDrop(solvablePhrase, letter);
                }
            }
            v.invalidate();
            return true;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {

        Phrase phrase = getList().get(position);
        holder.bindTo(phrase);
    }
}


