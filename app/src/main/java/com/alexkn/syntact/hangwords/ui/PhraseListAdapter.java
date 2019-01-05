package com.alexkn.syntact.hangwords.ui;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.api.to.SolvablePhrase;
import com.alexkn.syntact.hangwords.ui.util.DropLetterOnPhraseListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhraseListAdapter extends ListItemAdapter<SolvablePhrase, PhraseViewHolder> {

    private DropLetterOnPhraseListener dropLetterOnPhraseListener;

    PhraseListAdapter(DropLetterOnPhraseListener dropLetterOnPhraseListener) {

        this.dropLetterOnPhraseListener = dropLetterOnPhraseListener;
    }

    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phrase_card, parent, false);
        PhraseViewHolder viewHolder = new PhraseViewHolder(view);

        viewHolder.itemView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                ClipData.Item item = event.getClipData().getItemAt(0);
                int id = Integer.parseInt(item.getText().toString());
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    SolvablePhrase solvablePhrase = this.getList().get(adapterPosition);
                    return dropLetterOnPhraseListener.handleDrop(solvablePhrase, id);
                }
            }
            v.invalidate();
            return true;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {
        SolvablePhrase phrase = getList().get(position);
        holder.bindTo(phrase);
    }
}
