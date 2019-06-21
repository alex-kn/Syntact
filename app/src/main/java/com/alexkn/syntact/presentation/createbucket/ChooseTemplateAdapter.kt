package com.alexkn.syntact.presentation.createbucket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.alexkn.syntact.R
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.alexkn.syntact.restservice.Template

class ChooseTemplateAdapter : ListItemAdapter<Template, ChooseTemplateAdapter.ChooseTemplateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseTemplateViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.bucket_create_choose_template_card, parent, false)

        return ChooseTemplateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChooseTemplateViewHolder, position: Int) {

        val template = list[position]
        holder.bindTo(template)
    }

    class ChooseTemplateViewHolder(itemView: View) : ListItemViewHolder<Template>(itemView) {

        private var textView: TextView = itemView.findViewById(R.id.chooseTemplateTextView)

        override fun bindTo(template: Template) {

            textView.text = template.id + template.name
        }
    }
}

