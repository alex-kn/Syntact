package com.alexkn.syntact.presentation.hangman;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.SolvableItem;

public class PhraseViewHolder extends ListItemViewHolder<SolvableItem> {

    private TextView clueTextView;

    private TextView solutionTextView;

    private String clue;

    private String solution;

    private View.OnDragListener dragListener;

    PhraseViewHolder(View v) {

        super(v);
        v.setFocusable(false);

        clueTextView = v.findViewById(R.id.clueTextView);
        solutionTextView = v.findViewById(R.id.solutionTextView);
        clueTextView.setText(clue);
        solutionTextView.setText(solution);
    }

    public void setOnDrag(View.OnDragListener dragListener) {

        this.dragListener = dragListener;
        itemView.setOnDragListener(dragListener);
    }

    @Override
    public void bindTo(SolvableItem solvableItem) {

        if (clueTextView != null) {
            clueTextView.setText(solvableItem.getClue().getText());
        }
        if (solutionTextView != null) {
            solutionTextView.setText(solvableItem.getAttempt());
        }
        clue = solvableItem.getClue().getText();
        solution = solvableItem.getAttempt();
    }
}
