package com.alexkn.syntact.hangwords.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class PhraseItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        holder.itemView.setTranslationY(- height - holder.itemView.getHeight());
        holder.itemView.animate().translationY(0).setInterpolator(new LinearInterpolator())
                .setDuration(700).setListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                dispatchAddFinished(holder);
            }
        }).start();
        return false;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {

        return true;
    }
}


