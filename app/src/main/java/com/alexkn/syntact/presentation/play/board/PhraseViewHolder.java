package com.alexkn.syntact.presentation.play.board;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

public class PhraseViewHolder extends ListItemViewHolder<SolvableTranslationCto> {

    private TextView clueTextView;

    private TextView solutionTextView;

    private View.OnDragListener dragListener;

    PhraseViewHolder(View v) {

        super(v);
        v.setFocusable(false);

        clueTextView = v.findViewById(R.id.clueTextView);
        solutionTextView = v.findViewById(R.id.solutionTextView);
    }

    public void setOnDrag(View.OnDragListener dragListener) {

        this.dragListener = dragListener;
        itemView.setOnDragListener(dragListener);
    }

    @Override
    public void bindTo(SolvableTranslationCto solvableTranslation) {

        clueTextView.setText(solvableTranslation.getClue().getText());
        solutionTextView.setText(solvableTranslation.getAttempt().getText());
    }
}
