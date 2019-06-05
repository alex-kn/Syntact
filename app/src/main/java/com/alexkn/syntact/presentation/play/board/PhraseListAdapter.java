package com.alexkn.syntact.presentation.play.board;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.presentation.common.ListItemAdapter;

public class PhraseListAdapter extends ListItemAdapter<SolvableTranslationCto, PhraseViewHolder> {

    private ViewModelCallback viewModelCallback;

    public PhraseListAdapter(ViewModelCallback viewModelCallback) {

        this.viewModelCallback = viewModelCallback;
    }

    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_item_card, parent, false);
        PhraseViewHolder viewHolder = new PhraseViewHolder(view);

        viewHolder.setOnDrag((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Letter letter = (Letter) event.getLocalState();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    SolvableTranslationCto solvableTranslation = this.getList()
                            .get(adapterPosition);
                    return viewModelCallback.handleDrop(solvableTranslation, letter);
                }
            }
            v.invalidate();
            return true;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {

        SolvableTranslationCto solvableTranslation = getList().get(position);
        holder.bindTo(solvableTranslation);
    }
}


