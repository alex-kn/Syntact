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
        when (list.size) {
            0 -> return
            else -> holder.bindTo(list[position % list.size])
        }

    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    fun getItemAt(position: Int): Template = list[position]

    class ChooseTemplateViewHolder(itemView: View) : ListItemViewHolder<Template>(itemView) {

        private var textView: TextView = itemView.findViewById(R.id.chooseTemplateTextView)

        override fun bindTo(entity: Template) {

            textView.text = entity.id + entity.name
        }
    }
}

