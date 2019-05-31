package com.alexkn.syntact.presentation.template.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.presentation.common.ListItemAdapter;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

public class TemplateAdapter extends ListItemAdapter<Template, TemplateAdapter.TemplateViewHolder> {

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_card, parent, false);
        return new TemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {

        Template template = getList().get(position);
        holder.bindTo(template);
    }

    static class TemplateViewHolder extends ListItemViewHolder<Template> {

        TextView textView;

        TemplateViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.templateLabel);
        }

        @Override
        public void bindTo(Template entity) {

            textView.setText(entity.getName());
        }
    }
}
