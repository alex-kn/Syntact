package com.alexkn.syntact.presentation.hangman;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Letter;
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

        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT));
        frameLayout.setBackgroundTintList(parent.getContext().getColorStateList(R.color.hangwords_card_background_color));
        PhraseViewHolder viewHolder = new PhraseViewHolder(frameLayout);

        viewHolder.setOnDrag((v, event) -> {
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

//        viewHolder.itemView.setOnDragListener((v, event) -> {
//            if (event.getAction() == DragEvent.ACTION_DROP) {
//                int adapterPosition = viewHolder.getAdapterPosition();
//                Letter letter = (Letter) event.getLocalState();
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    Phrase solvablePhrase = this.getList().get(adapterPosition);
//                    return viewModelCallback.handleDrop(solvablePhrase, letter);
//                }
//            }
//            v.invalidate();
//            return true;
//        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {

        Phrase phrase = getList().get(position);
        holder.bindTo(phrase);
    }
}


