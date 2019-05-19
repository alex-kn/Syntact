package com.alexkn.syntact.presentation.addlanguage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alexkn.syntact.R;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BucketOptionsAdapter
        extends RecyclerView.Adapter<BucketOptionsAdapter.BucketOptionsViewHolder> {

    private List<List<Locale>> dataset;

    private BiConsumer<Integer, Locale> callback;

    private RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();

    public BucketOptionsAdapter(List<List<Locale>> dataset, BiConsumer<Integer, Locale> callback) {

        this.callback = callback;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public BucketOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_language_choose_parent, parent, false);

        return new BucketOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketOptionsViewHolder holder, int position) {

        if (position == 0) {
            holder.textView.setText("Source Language");
        } else if (position == 1) {
            holder.textView.setText("Target Language");
        }

        List<Locale> locales = dataset.get(position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView.getContext(), RecyclerView.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(4);

        ChooseLanguageAdapter adapter = new ChooseLanguageAdapter(locales);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setRecycledViewPool(pool);holder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = layoutManager
                            .findFirstVisibleItemPosition();
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        Locale item = adapter.getItemAt(itemPosition);
                        callback.accept(holder.getAdapterPosition(), item);
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return dataset.size();
    }

    static class BucketOptionsViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView textView;

        BucketOptionsViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.chooseHeader);
            recyclerView = itemView.findViewById(R.id.chooseLanguageRecyclerView);
            LinearSnapHelper linearSnapHelper = new LinearSnapHelper();

            linearSnapHelper.attachToRecyclerView(recyclerView);
        }
    }
}
