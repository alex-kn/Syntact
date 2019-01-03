package com.alexkn.syntact.hangwords.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.PhraseSolver;
import com.alexkn.syntact.hangwords.logic.SolvablePhrase;
import com.google.android.material.card.MaterialCardView;

import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

class PhraseViewHolder extends ListItemViewHolder<SolvablePhrase> {

    private TextView clueTextView;
    private TextView solutionTextView;

    private SolvablePhrase phrase;

    PhraseViewHolder(View v) {
        super(v);
        v.setFocusable(false);
        clueTextView = v.findViewById(R.id.clueTextView);
        solutionTextView = v.findViewById(R.id.solutionTextView);
    }

    @Override
    public void bindTo(SolvablePhrase phrase) {
        this.phrase = phrase;
        clueTextView.setText(phrase.getClue());
        solutionTextView.setText(phrase.getCurrentText());
    }
}
