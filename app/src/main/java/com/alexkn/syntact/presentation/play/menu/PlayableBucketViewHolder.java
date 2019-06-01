package com.alexkn.syntact.presentation.play.menu;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

public class PlayableBucketViewHolder extends ListItemViewHolder<Bucket> {

    private final TextView languageLabel;

    private Bucket bucket;

    PlayableBucketViewHolder(View v) {

        super(v);
        languageLabel = v.findViewById(R.id.langLabel);
    }

    @Override
    public void bindTo(Bucket bucket) {

        this.bucket = bucket;

        languageLabel.setText("Bucket");

        itemView.setOnClickListener(this::startHangman);
    }

    private void startHangman(View view) {

        PlayMenuFragmentDirections.ActionPlayMenuFragmentToBoardFragment action = PlayMenuFragmentDirections
                .actionPlayMenuFragmentToBoardFragment(bucket.getId());
        Navigation.findNavController(view).navigate(action);
    }
}
