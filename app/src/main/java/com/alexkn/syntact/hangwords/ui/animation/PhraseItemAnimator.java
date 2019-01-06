package com.alexkn.syntact.hangwords.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class PhraseItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        holder.itemView.setTranslationY(-height - holder.itemView.getHeight());
        holder.itemView.animate().translationY(0).setInterpolator(new LinearInterpolator())
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        dispatchAddFinished(holder);
                    }
                }).start();
        return false;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX,
            int toY) {

        if (fromY > toY) {
            holder.itemView.setTranslationY(fromY - toY);
            holder.itemView.setElevation(-999);

            ObjectAnimator firstAnimation = ObjectAnimator
                    .ofFloat(holder.itemView, View.TRANSLATION_X,
                            -holder.itemView.getWidth() * 1.5f);

            firstAnimation.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {

                    holder.itemView.setTranslationY(0);
                    holder.itemView.setTranslationX(0);
                    holder.itemView.setElevation(0);
                    dispatchMoveFinished(holder);
                }
            });
            firstAnimation.start();
            return false;
        } else {
            return super.animateMove(holder, fromX, fromY, toX, toY);
        }
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {

        return true;
    }
}


