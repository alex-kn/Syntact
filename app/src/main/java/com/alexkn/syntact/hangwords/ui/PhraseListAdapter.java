package com.alexkn.syntact.hangwords.ui;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.LetterManagement;
import com.alexkn.syntact.hangwords.logic.PhraseManagement;
import com.alexkn.syntact.hangwords.logic.PhraseSolver;
import com.alexkn.syntact.hangwords.logic.SolvablePhrase;
import com.alexkn.syntact.hangwords.util.DiffCallback;
import com.alexkn.syntact.hangwords.util.Payload;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class PhraseListAdapter extends ListItemAdapter<SolvablePhrase, PhraseViewHolder> {
    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phrase_card, parent, false);
        return new PhraseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {
        SolvablePhrase phrase = getList().get(position);
        holder.bindTo(phrase);
        holder.itemView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                ClipData.Item item = event.getClipData().getItemAt(0);
                int id = Integer.parseInt(item.getText().toString());
                if (PhraseSolver.getInstance().isCharacterCorrect(phrase, id)) {
                    SolvablePhrase newPhrase = PhraseSolver.getInstance().solve(phrase, id);
                    holder.bindTo(newPhrase);
                    LetterManagement.getInstance().removeLetter(id);
                    if (!newPhrase.getCurrentText().contains("_")) {
                        PhraseManagement.getInstance().solvePhrase(newPhrase.getId());
                    }

                } else {
                    return false;
                }

            }
            v.invalidate();
            return true;
        });
    }

    //    @Override
    //    DiffUtil.ItemCallback<SolvablePhrase> onDiff() {
    //        return new DiffCallback<SolvablePhrase>(){
    //            @Nullable
    //            @Override
    //            public Payload getChangePayload(@NonNull SolvablePhrase oldItem,
    //                    @NonNull SolvablePhrase newItem) {
    //                if (!newItem.getCurrentText().contains("_")) {
    //                    return new Payload(true);
    //                }
    //                return null;
    //            }
    //        };
    //    }
}
