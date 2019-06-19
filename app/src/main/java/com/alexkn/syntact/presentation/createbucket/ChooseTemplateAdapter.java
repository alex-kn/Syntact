package com.alexkn.syntact.presentation.createbucket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.restservice.Template;

import java.util.ArrayList;
import java.util.List;

public class ChooseTemplateAdapter extends RecyclerView.Adapter<ChooseTemplateAdapter.ChooseTemplateViewHolder> {

    private List<Template> dataset = new ArrayList<>();

    @NonNull
    @Override
    public ChooseTemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_create_choose_template_card, parent, false);

        return new ChooseTemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseTemplateViewHolder holder, int position) {

        holder.textView.setText(dataset.get(position).getId() + dataset.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return dataset.size();
    }

    public Template getItemAt(int position) {

        return dataset.get(position);
    }

    public void setData(List<Template> dataset) {

        this.dataset = dataset;
        notifyDataSetChanged();
    }

    static class ChooseTemplateViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ChooseTemplateViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.chooseTemplateTextView);
        }
    }
}

