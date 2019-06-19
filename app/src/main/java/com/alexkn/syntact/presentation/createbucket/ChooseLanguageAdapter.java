package com.alexkn.syntact.presentation.createbucket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;

import java.util.List;
import java.util.Locale;

public class ChooseLanguageAdapter
        extends RecyclerView.Adapter<ChooseLanguageAdapter.ChooseLanguageViewHolder> {

    private List<Locale> dataset;

    public ChooseLanguageAdapter(List<Locale> dataset) {

        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ChooseLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bucket_create_choose_child, parent, false);

        return new ChooseLanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseLanguageViewHolder holder, int position) {

        holder.textView.setText(dataset.get(position).getDisplayLanguage());
    }

    @Override
    public int getItemCount() {

        return dataset.size();
    }

    public Locale getItemAt(int position) {

        return dataset.get(position);
    }

    static class ChooseLanguageViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ChooseLanguageViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.chooseLanguageTextView);
        }
    }
}
