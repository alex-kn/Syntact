package com.alexkn.syntact.presentation.createbucket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil

import com.alexkn.syntact.R
import com.alexkn.syntact.databinding.BucketCreateChooseTemplateCardBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.alexkn.syntact.restservice.Template
import com.google.android.material.button.MaterialButton
import java.util.function.Consumer
import kotlin.Int.Companion.MAX_VALUE

class ChooseTemplateAdapter : ListItemAdapter<Template, ChooseTemplateAdapter.ChooseTemplateViewHolder>() {

    lateinit var createListener: Consumer<Template>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseTemplateViewHolder {

        val binding = DataBindingUtil.inflate<BucketCreateChooseTemplateCardBinding>(LayoutInflater.from(parent.context), R.layout.bucket_create_choose_template_card, parent, false)

        return ChooseTemplateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseTemplateViewHolder, position: Int) {

        val template = list[position]
        holder.bindTo(template)
        holder.createButton.setOnClickListener { createListener.accept(template) }

    }

    class ChooseTemplateViewHolder(var databinding: BucketCreateChooseTemplateCardBinding) : ListItemViewHolder<Template>(databinding.root) {

        var createButton: MaterialButton = itemView.findViewById(R.id.chooseButton)

        override fun bindTo(entity: Template) {
            databinding.template = entity

        }
    }
}

